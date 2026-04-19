package uz.transliterator.service;

import org.springframework.stereotype.Service;
import uz.transliterator.entity.ConversionType;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TransliterationService {

    // Kirill → Yangi oʻzbek transkripsiyasi
    private static final Map<String, String> CYRILLIC_TO_NEW_LATIN = new LinkedHashMap<>();

    // Kirill → Anʼanaviy transkripsiya
    private static final Map<String, String> CYRILLIC_TO_TRADITIONAL_LATIN = new LinkedHashMap<>();

    // Yangi lotin → Anʼanaviy lotin
    private static final Map<String, String> NEW_TO_TRADITIONAL = new LinkedHashMap<>();

    static {
        // ===== Kirill → Yangi oʻzbek transkripsiyasi =====
        // Ikki harfli kombinatsiyalar birinchi
        CYRILLIC_TO_NEW_LATIN.put("\u041D\u0433", "Ng");  // Нг
        CYRILLIC_TO_NEW_LATIN.put("\u043D\u0433", "ng");  // нг
        CYRILLIC_TO_NEW_LATIN.put("\u0427", "Ch");  // Ч
        CYRILLIC_TO_NEW_LATIN.put("\u0447", "ch");  // ч
        CYRILLIC_TO_NEW_LATIN.put("\u0428", "Sh");  // Ш
        CYRILLIC_TO_NEW_LATIN.put("\u0448", "sh");  // ш
        CYRILLIC_TO_NEW_LATIN.put("\u0429", "Sh");  // Щ
        CYRILLIC_TO_NEW_LATIN.put("\u0449", "sh");  // щ
        CYRILLIC_TO_NEW_LATIN.put("\u0401", "Yo");  // Ё
        CYRILLIC_TO_NEW_LATIN.put("\u0451", "yo");  // ё
        CYRILLIC_TO_NEW_LATIN.put("\u042E", "Yu");  // Ю
        CYRILLIC_TO_NEW_LATIN.put("\u044E", "yu");  // ю
        CYRILLIC_TO_NEW_LATIN.put("\u042F", "Ya");  // Я
        CYRILLIC_TO_NEW_LATIN.put("\u044F", "ya");  // я
        CYRILLIC_TO_NEW_LATIN.put("\u0426", "Ts");  // Ц
        CYRILLIC_TO_NEW_LATIN.put("\u0446", "ts");  // ц

        // Bir harfli - docx jadvali bo'yicha
        CYRILLIC_TO_NEW_LATIN.put("\u0410", "A");   // А
        CYRILLIC_TO_NEW_LATIN.put("\u0430", "a");   // а
        CYRILLIC_TO_NEW_LATIN.put("\u0411", "B");   // Б
        CYRILLIC_TO_NEW_LATIN.put("\u0431", "b");   // б
        CYRILLIC_TO_NEW_LATIN.put("\u0412", "V");   // В
        CYRILLIC_TO_NEW_LATIN.put("\u0432", "v");   // в
        CYRILLIC_TO_NEW_LATIN.put("\u0413", "G");   // Г
        CYRILLIC_TO_NEW_LATIN.put("\u0433", "g");   // г
        CYRILLIC_TO_NEW_LATIN.put("\u0492", "G\u02BB");  // Ғ → Gʻ
        CYRILLIC_TO_NEW_LATIN.put("\u0493", "g\u02BB");  // ғ → gʻ
        CYRILLIC_TO_NEW_LATIN.put("\u0414", "D");   // Д
        CYRILLIC_TO_NEW_LATIN.put("\u0434", "d");   // д
        CYRILLIC_TO_NEW_LATIN.put("\u0415", "E");   // Е
        CYRILLIC_TO_NEW_LATIN.put("\u0435", "e");   // е
        CYRILLIC_TO_NEW_LATIN.put("\u0416", "J");   // Ж
        CYRILLIC_TO_NEW_LATIN.put("\u0436", "j");   // ж
        CYRILLIC_TO_NEW_LATIN.put("\u0417", "Z");   // З
        CYRILLIC_TO_NEW_LATIN.put("\u0437", "z");   // з
        CYRILLIC_TO_NEW_LATIN.put("\u0418", "I");   // И
        CYRILLIC_TO_NEW_LATIN.put("\u0438", "i");   // и
        CYRILLIC_TO_NEW_LATIN.put("\u042B", "I");   // Ы → I
        CYRILLIC_TO_NEW_LATIN.put("\u044B", "i");   // ы → i
        CYRILLIC_TO_NEW_LATIN.put("\u0419", "Y");   // Й
        CYRILLIC_TO_NEW_LATIN.put("\u0439", "y");   // й
        CYRILLIC_TO_NEW_LATIN.put("\u041A", "K");   // К
        CYRILLIC_TO_NEW_LATIN.put("\u043A", "k");   // к
        CYRILLIC_TO_NEW_LATIN.put("\u049A", "Q");   // Қ
        CYRILLIC_TO_NEW_LATIN.put("\u049B", "q");   // қ
        CYRILLIC_TO_NEW_LATIN.put("\u041B", "L");   // Л
        CYRILLIC_TO_NEW_LATIN.put("\u043B", "l");   // л
        CYRILLIC_TO_NEW_LATIN.put("\u041C", "M");   // М
        CYRILLIC_TO_NEW_LATIN.put("\u043C", "m");   // м
        CYRILLIC_TO_NEW_LATIN.put("\u041D", "N");   // Н
        CYRILLIC_TO_NEW_LATIN.put("\u043D", "n");   // н
        CYRILLIC_TO_NEW_LATIN.put("\u041E", "O\u2019");  // О → O'
        CYRILLIC_TO_NEW_LATIN.put("\u043E", "o\u2019");  // о → o'
        CYRILLIC_TO_NEW_LATIN.put("\u04E8", "O\u2019");  // Ө → O'
        CYRILLIC_TO_NEW_LATIN.put("\u04E9", "o\u2019");  // ө → o'
        CYRILLIC_TO_NEW_LATIN.put("\u041F", "P");   // П
        CYRILLIC_TO_NEW_LATIN.put("\u043F", "p");   // п
        CYRILLIC_TO_NEW_LATIN.put("\u0420", "R");   // Р
        CYRILLIC_TO_NEW_LATIN.put("\u0440", "r");   // р
        CYRILLIC_TO_NEW_LATIN.put("\u0421", "S");   // С
        CYRILLIC_TO_NEW_LATIN.put("\u0441", "s");   // с
        CYRILLIC_TO_NEW_LATIN.put("\u0422", "T");   // Т
        CYRILLIC_TO_NEW_LATIN.put("\u0442", "t");   // т
        CYRILLIC_TO_NEW_LATIN.put("\u0423", "U");   // У
        CYRILLIC_TO_NEW_LATIN.put("\u0443", "u");   // у
        CYRILLIC_TO_NEW_LATIN.put("\u04AE", "U");   // Ү → U
        CYRILLIC_TO_NEW_LATIN.put("\u04AF", "u");   // ү → u
        CYRILLIC_TO_NEW_LATIN.put("\u0424", "F");   // Ф
        CYRILLIC_TO_NEW_LATIN.put("\u0444", "f");   // ф
        CYRILLIC_TO_NEW_LATIN.put("\u0425", "X");   // Х
        CYRILLIC_TO_NEW_LATIN.put("\u0445", "x");   // х
        CYRILLIC_TO_NEW_LATIN.put("\u04B2", "H");   // Ҳ
        CYRILLIC_TO_NEW_LATIN.put("\u04B3", "h");   // ҳ
        CYRILLIC_TO_NEW_LATIN.put("\u042A", "");    // Ъ
        CYRILLIC_TO_NEW_LATIN.put("\u044A", "");    // ъ
        CYRILLIC_TO_NEW_LATIN.put("\u042C", "");    // Ь
        CYRILLIC_TO_NEW_LATIN.put("\u044C", "");    // ь
        CYRILLIC_TO_NEW_LATIN.put("\u042D", "E");   // Э
        CYRILLIC_TO_NEW_LATIN.put("\u044D", "e");   // э
        CYRILLIC_TO_NEW_LATIN.put("\u040E", "O\u02BB");  // Ў → Oʻ
        CYRILLIC_TO_NEW_LATIN.put("\u045E", "o\u02BB");  // ў → oʻ

        // ===== Kirill → Anʼanaviy transkripsiya =====
        // Ikki harfli kombinatsiyalar birinchi
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u041D\u0433", "\u01A0");  // Нг → Ƞ
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u043D\u0433", "\u019E");  // нг → ƞ
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0427", "\u010C");  // Ч → Č
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0447", "\u010D");  // ч → č
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0428", "\u0160");  // Ш → Š
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0448", "\u0161");  // ш → š
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0429", "\u0160");  // Щ → Š
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0449", "\u0161");  // щ → š
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0401", "Yo");  // Ё
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0451", "yo");  // ё
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u042E", "Yu");  // Ю
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u044E", "yu");  // ю
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u042F", "Ya");  // Я
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u044F", "ya");  // я
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0426", "Ts");  // Ц
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0446", "ts");  // ц

        // Bir harfli
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0410", "A");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0430", "a");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0411", "B");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0431", "b");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0412", "V");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0432", "v");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0413", "G");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0433", "g");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0492", "\u0194");  // Ғ → Ɣ
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0493", "\u0263");  // ғ → ɣ
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0414", "D");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0434", "d");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0415", "E");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0435", "e");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0416", "\u01EE");  // Ж → Ǯ
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0436", "\u01EF");  // ж → ǯ
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0417", "Z");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0437", "z");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0418", "I:");  // И → I:
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0438", "i:");  // и → i:
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u042B", "\u00CF:");  // Ы → Ï:
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u044B", "\u00EF:");  // ы → ï:
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0419", "J");   // Й → J
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0439", "j");   // й → j
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u041A", "K");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u043A", "k");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u049A", "Q");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u049B", "q");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u041B", "L");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u043B", "l");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u041C", "M");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u043C", "m");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u041D", "N");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u043D", "n");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u041E", "O");   // О → O
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u043E", "o");   // о → o
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u04E8", "\u00D6");  // Ө → Ö
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u04E9", "\u00F6");  // ө → ö
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u041F", "P");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u043F", "p");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0420", "R");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0440", "r");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0421", "S");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0441", "s");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0422", "T");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0442", "t");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0423", "U");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0443", "u");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u04AE", "\u00DC");  // Ү → Ü
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u04AF", "\u00FC");  // ү → ü
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0424", "F");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0444", "f");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0425", "X");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u0445", "x");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u04B2", "H");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u04B3", "h");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u042A", "i");   // Ъ → i
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u044A", "i");   // ъ → i
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u042C", "\u00EF");  // Ь → ï
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u044C", "\u00EF");  // ь → ï
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u042D", "E");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u044D", "e");
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u040E", "O\u02BB");  // Ў → Oʻ
        CYRILLIC_TO_TRADITIONAL_LATIN.put("\u045E", "o\u02BB");  // ў → oʻ

        // ===== Yangi lotin → Anʼanaviy lotin =====
        // Ikki/uch harfli birinchi
        NEW_TO_TRADITIONAL.put("G\u02BB", "\u0194");   // Gʻ → Ɣ
        NEW_TO_TRADITIONAL.put("g\u02BB", "\u0263");   // gʻ → ɣ
        NEW_TO_TRADITIONAL.put("O\u02BB", "O\u02BB");  // Oʻ → Oʻ (o'zgarishsiz)
        NEW_TO_TRADITIONAL.put("o\u02BB", "o\u02BB");
        NEW_TO_TRADITIONAL.put("O\u2019", "O");        // O' → O
        NEW_TO_TRADITIONAL.put("o\u2019", "o");        // o' → o
        NEW_TO_TRADITIONAL.put("Ng", "\u01A0");        // Ng → Ƞ
        NEW_TO_TRADITIONAL.put("ng", "\u019E");        // ng → ƞ
        NEW_TO_TRADITIONAL.put("Ch", "\u010C");        // Ch → Č
        NEW_TO_TRADITIONAL.put("ch", "\u010D");        // ch → č
        NEW_TO_TRADITIONAL.put("Sh", "\u0160");        // Sh → Š
        NEW_TO_TRADITIONAL.put("sh", "\u0161");        // sh → š
        // Bir harfli
        NEW_TO_TRADITIONAL.put("J", "\u01EE");   // J → Ǯ
        NEW_TO_TRADITIONAL.put("j", "\u01EF");   // j → ǯ
        NEW_TO_TRADITIONAL.put("Y", "J");        // Y → J
        NEW_TO_TRADITIONAL.put("y", "j");        // y → j
        NEW_TO_TRADITIONAL.put("I", "I:");       // I → I:
        NEW_TO_TRADITIONAL.put("i", "i:");       // i → i:
    }

    public String convert(String text, ConversionType type) {
        return switch (type) {
            case CYRILLIC_TO_NEW_LATIN -> cyrillicToLatin(text, CYRILLIC_TO_NEW_LATIN);
            case CYRILLIC_TO_TRADITIONAL_LATIN -> cyrillicToLatin(text, CYRILLIC_TO_TRADITIONAL_LATIN);
            case NEW_LATIN_TO_TRADITIONAL_LATIN -> newLatinToTraditional(text);
        };
    }

    private String cyrillicToLatin(String text, Map<String, String> mapping) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            boolean replaced = false;

            // 2 ta harfli kombinatsiyalarni tekshirish (Нг, etc.)
            if (i + 1 < text.length()) {
                String twoChar = text.substring(i, i + 2);
                if (mapping.containsKey(twoChar)) {
                    result.append(mapping.get(twoChar));
                    i += 2;
                    replaced = true;
                }
            }

            if (!replaced) {
                char ch = text.charAt(i);
                // "Е" harfi so'z boshida "Ye" bo'ladi
                if (ch == '\u0415' && isWordStart(text, i)) {
                    result.append("Ye");
                    i++;
                } else if (ch == '\u0435' && isWordStart(text, i)) {
                    result.append("ye");
                    i++;
                } else {
                    String oneChar = String.valueOf(ch);
                    if (mapping.containsKey(oneChar)) {
                        result.append(mapping.get(oneChar));
                    } else {
                        result.append(ch);
                    }
                    i++;
                }
            }
        }
        return result.toString();
    }

    private boolean isWordStart(String text, int index) {
        if (index == 0) return true;
        char prev = text.charAt(index - 1);
        return !Character.isLetter(prev);
    }

    private String newLatinToTraditional(String text) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            boolean replaced = false;

            // 2 ta harfli kombinatsiyalar tekshirish
            if (i + 1 < text.length()) {
                String twoChar = text.substring(i, i + 2);
                if (NEW_TO_TRADITIONAL.containsKey(twoChar)) {
                    result.append(NEW_TO_TRADITIONAL.get(twoChar));
                    i += 2;
                    replaced = true;
                }
            }

            if (!replaced) {
                String oneChar = String.valueOf(text.charAt(i));
                if (NEW_TO_TRADITIONAL.containsKey(oneChar)) {
                    result.append(NEW_TO_TRADITIONAL.get(oneChar));
                } else {
                    result.append(text.charAt(i));
                }
                i++;
            }
        }
        return result.toString();
    }
}
