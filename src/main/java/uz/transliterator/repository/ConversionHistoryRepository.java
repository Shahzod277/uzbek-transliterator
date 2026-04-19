package uz.transliterator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.transliterator.entity.ConversionHistory;

import java.util.List;

@Repository
public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory, Long> {

    List<ConversionHistory> findTop10ByChatIdOrderByCreatedAtDesc(Long chatId);
}
