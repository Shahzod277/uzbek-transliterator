package uz.transliterator.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LegacyDataCleaner {

    private final JdbcTemplate jdbc;

    @PostConstruct
    public void migrateLegacyEnumValues() {
        try {
            jdbc.execute("ALTER TABLE user_state DROP CONSTRAINT IF EXISTS user_state_conversion_type_check");
            jdbc.execute("ALTER TABLE conversion_history DROP CONSTRAINT IF EXISTS conversion_history_conversion_type_check");
            log.info("Legacy check constraint'lar o'chirildi");
        } catch (Exception e) {
            log.warn("Constraint o'chirishda xatolik: {}", e.getMessage());
        }

        try {
            int us1 = jdbc.update("UPDATE user_state SET conversion_type = 'TRADITIONAL_TO_NEW' WHERE conversion_type IN ('CYRILLIC_TO_TRADITIONAL_LATIN', 'CYRILLIC_TO_NEW_LATIN')");
            int us2 = jdbc.update("UPDATE user_state SET conversion_type = 'LITERARY_TO_NEW' WHERE conversion_type = 'NEW_LATIN_TO_TRADITIONAL_LATIN'");
            int ch1 = jdbc.update("UPDATE conversion_history SET conversion_type = 'TRADITIONAL_TO_NEW' WHERE conversion_type IN ('CYRILLIC_TO_TRADITIONAL_LATIN', 'CYRILLIC_TO_NEW_LATIN')");
            int ch2 = jdbc.update("UPDATE conversion_history SET conversion_type = 'LITERARY_TO_NEW' WHERE conversion_type = 'NEW_LATIN_TO_TRADITIONAL_LATIN'");
            log.info("Legacy enum migratsiyasi: user_state={}, conversion_history={}", us1 + us2, ch1 + ch2);
        } catch (Exception e) {
            log.warn("Legacy migratsiya bajarilmadi: {}", e.getMessage());
        }
    }
}
