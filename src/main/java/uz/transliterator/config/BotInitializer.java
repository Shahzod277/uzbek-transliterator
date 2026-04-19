package uz.transliterator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.transliterator.bot.TransliteratorBot;

@Slf4j
@Component
public class BotInitializer {

    private final TransliteratorBot bot;

    public BotInitializer(TransliteratorBot bot) {
        this.bot = bot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
            log.info("Telegram bot muvaffaqiyatli ro'yxatdan o'tdi!");
        } catch (TelegramApiException e) {
            log.error("Telegram botni ro'yxatdan o'tkazishda xatolik: {}", e.getMessage());
            throw e;
        }
    }
}
