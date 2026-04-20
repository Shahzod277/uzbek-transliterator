package uz.transliterator.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.transliterator.config.BotConfig;
import uz.transliterator.entity.BotUser;
import uz.transliterator.entity.ConversionHistory;
import uz.transliterator.entity.ConversionType;
import uz.transliterator.entity.UserState;
import uz.transliterator.repository.BotUserRepository;
import uz.transliterator.repository.ConversionHistoryRepository;
import uz.transliterator.repository.UserStateRepository;
import uz.transliterator.service.TransliterationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TransliteratorBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final TransliterationService transliterationService;
    private final ConversionHistoryRepository historyRepository;
    private final UserStateRepository userStateRepository;
    private final BotUserRepository botUserRepository;

    private static final String BTN_LITERARY_TO_NEW = "\uD83D\uDD24 Adabiy → Yangi transkripsiya";
    private static final String BTN_LITERARY_TO_TRADITIONAL = "\uD83D\uDD24 Adabiy → An'anaviy (kirill)";
    private static final String BTN_TRADITIONAL_TO_NEW = "\uD83D\uDD04 An'anaviy (kirill) → Yangi";
    private static final String BTN_HISTORY = "\uD83D\uDCDC Tarix";
    private static final String BTN_BACK = "\u2B05\uFE0F Ortga";

    public TransliteratorBot(BotConfig botConfig,
                             TransliterationService transliterationService,
                             ConversionHistoryRepository historyRepository,
                             UserStateRepository userStateRepository,
                             BotUserRepository botUserRepository) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.transliterationService = transliterationService;
        this.historyRepository = historyRepository;
        this.userStateRepository = userStateRepository;
        this.botUserRepository = botUserRepository;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();

        try {
            // Kontakt yuborilsa
            if (update.getMessage().hasContact()) {
                handleContact(update);
                return;
            }

            // Matn yuborilsa
            if (!update.getMessage().hasText()) {
                return;
            }

            String text = update.getMessage().getText();

            // /start bo'lsa har doim kontakt so'rash yoki menyu ko'rsatish
            if (text.equals("/start")) {
                handleStart(chatId, update);
                return;
            }

            // Ro'yxatdan o'tmagan bo'lsa — kontakt so'rash
            if (!botUserRepository.existsById(chatId)) {
                askForContact(chatId);
                return;
            }

            String username = update.getMessage().getFrom().getUserName();

            switch (text) {
                case BTN_LITERARY_TO_NEW -> handleModeSelection(chatId, ConversionType.LITERARY_TO_NEW);
                case BTN_LITERARY_TO_TRADITIONAL -> handleModeSelection(chatId, ConversionType.LITERARY_TO_TRADITIONAL);
                case BTN_TRADITIONAL_TO_NEW -> handleModeSelection(chatId, ConversionType.TRADITIONAL_TO_NEW);
                case BTN_HISTORY -> handleHistory(chatId);
                case BTN_BACK -> showMainMenu(chatId);
                default -> handleTextConversion(chatId, username, text);
            }
        } catch (TelegramApiException e) {
            log.error("Telegram xatolik: chatId={}, error={}", chatId, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Kutilmagan xatolik: chatId={}, error={}", chatId, e.getMessage(), e);
            try {
                SendMessage err = new SendMessage();
                err.setChatId(chatId);
                err.setText("\u26A0\uFE0F Xatolik yuz berdi. /start ni bosib qaytadan urinib ko'ring.");
                execute(err);
            } catch (TelegramApiException ex) {
                log.error("Xatolik xabarini yuborib bo'lmadi: {}", ex.getMessage());
            }
        }
    }

    private void handleStart(Long chatId, Update update) throws TelegramApiException {
        userStateRepository.deleteById(chatId);

        // Agar allaqachon ro'yxatdan o'tgan bo'lsa — menyuni ko'rsatish
        if (botUserRepository.existsById(chatId)) {
            showMainMenu(chatId);
            return;
        }

        // Ro'yxatdan o'tmagan bo'lsa — salomlashib kontakt so'rash
        String welcomeText = """
                Assalomu alaykum! \uD83D\uDC4B

                Men O'zbek transliteratsiya botiman.

                Davom etish uchun telefon raqamingizni yuboring \u2B07\uFE0F""";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(welcomeText);
        message.setReplyMarkup(contactKeyboard());
        execute(message);
    }

    private void askForContact(Long chatId) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("\uD83D\uDCF1 Iltimos, avval telefon raqamingizni yuboring:");
        message.setReplyMarkup(contactKeyboard());
        execute(message);
    }

    private void handleContact(Update update) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        Contact contact = update.getMessage().getContact();

        BotUser user = BotUser.builder()
                .chatId(chatId)
                .username(update.getMessage().getFrom().getUserName())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .phoneNumber(contact.getPhoneNumber())
                .build();
        botUserRepository.save(user);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Rahmat! Siz muvaffaqiyatli ro'yxatdan o'tdingiz \u2705\n\nEndi rejimni tanlang:");
        message.setReplyMarkup(mainMenuKeyboard());
        execute(message);
    }

    private void showMainMenu(Long chatId) throws TelegramApiException {
        userStateRepository.deleteById(chatId);

        String menuText = """
                Rejimni tanlang:

                \uD83D\uDD24 Adabiy → Yangi transkripsiya
                \uD83D\uDD24 Adabiy → An'anaviy (kirill)
                \uD83D\uDD04 An'anaviy (kirill) → Yangi transkripsiya""";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(menuText);
        message.setReplyMarkup(mainMenuKeyboard());
        execute(message);
    }

    private void handleModeSelection(Long chatId, ConversionType type) throws TelegramApiException {
        UserState state = UserState.builder()
                .chatId(chatId)
                .conversionType(type)
                .build();
        userStateRepository.save(state);

        String modeText = switch (type) {
            case LITERARY_TO_NEW ->
                    "\uD83D\uDD24 Rejim: Adabiy → Yangi transkripsiya\n\nOʻzbek adabiy tilida matn yuboring:";
            case LITERARY_TO_TRADITIONAL ->
                    "\uD83D\uDD24 Rejim: Adabiy → An'anaviy (kirill)\n\nOʻzbek adabiy tilida matn yuboring:";
            case TRADITIONAL_TO_NEW ->
                    "\uD83D\uDD04 Rejim: An'anaviy (kirill) → Yangi transkripsiya\n\nAn'anaviy (kirill) yozuvida matn yuboring:";
        };

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(modeText);
        message.setReplyMarkup(backKeyboard());
        execute(message);
    }

    private void handleTextConversion(Long chatId, String username, String text) throws TelegramApiException {
        Optional<UserState> stateOpt = userStateRepository.findById(chatId);

        if (stateOpt.isEmpty()) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Iltimos, avval rejimni tanlang \u2B07\uFE0F");
            message.setReplyMarkup(mainMenuKeyboard());
            execute(message);
            return;
        }

        ConversionType type = stateOpt.get().getConversionType();
        String converted = transliterationService.convert(text, type);

        ConversionHistory history = ConversionHistory.builder()
                .chatId(chatId)
                .username(username)
                .originalText(text)
                .convertedText(converted)
                .conversionType(type)
                .build();
        historyRepository.save(history);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(converted);
        message.setReplyMarkup(backKeyboard());
        execute(message);
    }

    private void handleHistory(Long chatId) throws TelegramApiException {
        List<ConversionHistory> historyList = historyRepository.findTop10ByChatIdOrderByCreatedAtDesc(chatId);

        StringBuilder sb = new StringBuilder("\uD83D\uDCDC Oxirgi 10 ta konvertatsiya:\n\n");

        if (historyList.isEmpty()) {
            sb.append("Hali konvertatsiya qilinmagan.");
        } else {
            for (int i = 0; i < historyList.size(); i++) {
                ConversionHistory h = historyList.get(i);
                String typeName = switch (h.getConversionType()) {
                    case LITERARY_TO_NEW -> "Adabiy→Yangi";
                    case LITERARY_TO_TRADITIONAL -> "Adabiy→An'anaviy";
                    case TRADITIONAL_TO_NEW -> "An'anaviy→Yangi";
                };
                sb.append(i + 1).append(". [").append(typeName).append("]\n");
                sb.append("   ").append(truncate(h.getOriginalText(), 50)).append("\n");
                sb.append("   → ").append(truncate(h.getConvertedText(), 50)).append("\n\n");
            }
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(sb.toString());
        message.setReplyMarkup(mainMenuKeyboard());
        execute(message);
    }

    private String truncate(String text, int maxLen) {
        if (text.length() <= maxLen) return text;
        return text.substring(0, maxLen) + "...";
    }

    private ReplyKeyboardMarkup contactKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton contactBtn = new KeyboardButton("\uD83D\uDCF1 Telefon raqamni yuborish");
        contactBtn.setRequestContact(true);
        row.add(contactBtn);
        rows.add(row);

        markup.setKeyboard(rows);
        return markup;
    }

    private ReplyKeyboardMarkup mainMenuKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(BTN_LITERARY_TO_NEW));
        rows.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(BTN_LITERARY_TO_TRADITIONAL));
        rows.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton(BTN_TRADITIONAL_TO_NEW));
        rows.add(row3);

        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton(BTN_HISTORY));
        rows.add(row4);

        markup.setKeyboard(rows);
        return markup;
    }

    private ReplyKeyboardMarkup backKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton(BTN_BACK));
        rows.add(row);

        markup.setKeyboard(rows);
        return markup;
    }
}
