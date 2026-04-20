package uz.transliterator.service;

import org.springframework.stereotype.Service;
import uz.transliterator.entity.ConversionType;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TransliterationService {

    // Oʻzbek adabiy tili (lotin) → Yangi oʻzbek transkripsiyasi
    private static final Map<String, String> LITERARY_TO_NEW = new LinkedHashMap<>();

    // Oʻzbek adabiy tili (lotin) → Anʼanaviy transkripsiya (kirill)
    private static final Map<String, String> LITERARY_TO_TRADITIONAL = new LinkedHashMap<>();

    // Anʼanaviy transkripsiya (kirill) → Yangi oʻzbek transkripsiyasi
    private static final Map<String, String> TRADITIONAL_TO_NEW = new LinkedHashMap<>();

    static {
        // ===== Adabiy → Yangi transkripsiya =====
        // 2 harfli birikmalar (birinchi!)
        putApostrophePairs(LITERARY_TO_NEW, "G", "\u0393");  // Gʻ → Γ
        putApostrophePairs(LITERARY_TO_NEW, "g", "\u03B3");  // gʻ → γ
        putApostrophePairs(LITERARY_TO_NEW, "O", "O");       // Oʻ → O
        putApostrophePairs(LITERARY_TO_NEW, "o", "o");       // oʻ → o
        LITERARY_TO_NEW.put("Ng", "\u01A0"); LITERARY_TO_NEW.put("NG", "\u01A0"); LITERARY_TO_NEW.put("ng", "\u019E");
        LITERARY_TO_NEW.put("Ch", "\u010C"); LITERARY_TO_NEW.put("CH", "\u010C"); LITERARY_TO_NEW.put("ch", "\u010D");
        LITERARY_TO_NEW.put("Sh", "\u0160"); LITERARY_TO_NEW.put("SH", "\u0160"); LITERARY_TO_NEW.put("sh", "\u0161");

        // 1 harfli
        LITERARY_TO_NEW.put("A", "A"); LITERARY_TO_NEW.put("a", "a");
        LITERARY_TO_NEW.put("B", "B"); LITERARY_TO_NEW.put("b", "b");
        LITERARY_TO_NEW.put("V", "V"); LITERARY_TO_NEW.put("v", "v");
        LITERARY_TO_NEW.put("G", "G"); LITERARY_TO_NEW.put("g", "g");
        LITERARY_TO_NEW.put("D", "D"); LITERARY_TO_NEW.put("d", "d");
        LITERARY_TO_NEW.put("E", "E"); LITERARY_TO_NEW.put("e", "e");
        LITERARY_TO_NEW.put("J", "\u01EE"); LITERARY_TO_NEW.put("j", "\u01EF");  // Ǯ / ǯ
        LITERARY_TO_NEW.put("Z", "Z"); LITERARY_TO_NEW.put("z", "z");
        LITERARY_TO_NEW.put("I", "I"); LITERARY_TO_NEW.put("i", "i");
        LITERARY_TO_NEW.put("Y", "J"); LITERARY_TO_NEW.put("y", "j");
        LITERARY_TO_NEW.put("K", "K"); LITERARY_TO_NEW.put("k", "k");
        LITERARY_TO_NEW.put("Q", "Q"); LITERARY_TO_NEW.put("q", "q");
        LITERARY_TO_NEW.put("L", "L"); LITERARY_TO_NEW.put("l", "l");
        LITERARY_TO_NEW.put("M", "M"); LITERARY_TO_NEW.put("m", "m");
        LITERARY_TO_NEW.put("N", "N"); LITERARY_TO_NEW.put("n", "n");
        LITERARY_TO_NEW.put("O", "\u0100"); LITERARY_TO_NEW.put("o", "\u0101");  // Ā / ā
        LITERARY_TO_NEW.put("P", "P"); LITERARY_TO_NEW.put("p", "p");
        LITERARY_TO_NEW.put("R", "R"); LITERARY_TO_NEW.put("r", "r");
        LITERARY_TO_NEW.put("S", "S"); LITERARY_TO_NEW.put("s", "s");
        LITERARY_TO_NEW.put("T", "T"); LITERARY_TO_NEW.put("t", "t");
        LITERARY_TO_NEW.put("U", "U"); LITERARY_TO_NEW.put("u", "u");
        LITERARY_TO_NEW.put("F", "F"); LITERARY_TO_NEW.put("f", "f");
        LITERARY_TO_NEW.put("X", "X"); LITERARY_TO_NEW.put("x", "x");
        LITERARY_TO_NEW.put("H", "H"); LITERARY_TO_NEW.put("h", "h");

        // ===== Adabiy → Anʼanaviy (Kirill) =====
        putApostrophePairs(LITERARY_TO_TRADITIONAL, "G", "\u0492");  // Gʻ → Ғ
        putApostrophePairs(LITERARY_TO_TRADITIONAL, "g", "\u0493");  // gʻ → ғ
        putApostrophePairs(LITERARY_TO_TRADITIONAL, "O", "\u041E");  // Oʻ → О
        putApostrophePairs(LITERARY_TO_TRADITIONAL, "o", "\u043E");  // oʻ → о
        LITERARY_TO_TRADITIONAL.put("Ng", "\u041D\u0433"); LITERARY_TO_TRADITIONAL.put("NG", "\u041D\u0413"); LITERARY_TO_TRADITIONAL.put("ng", "\u043D\u0433");
        LITERARY_TO_TRADITIONAL.put("Ch", "\u0427"); LITERARY_TO_TRADITIONAL.put("CH", "\u0427"); LITERARY_TO_TRADITIONAL.put("ch", "\u0447");
        LITERARY_TO_TRADITIONAL.put("Sh", "\u0428"); LITERARY_TO_TRADITIONAL.put("SH", "\u0428"); LITERARY_TO_TRADITIONAL.put("sh", "\u0448");

        LITERARY_TO_TRADITIONAL.put("A", "\u0410"); LITERARY_TO_TRADITIONAL.put("a", "\u0430");
        LITERARY_TO_TRADITIONAL.put("B", "\u0411"); LITERARY_TO_TRADITIONAL.put("b", "\u0431");
        LITERARY_TO_TRADITIONAL.put("V", "\u0412"); LITERARY_TO_TRADITIONAL.put("v", "\u0432");
        LITERARY_TO_TRADITIONAL.put("G", "\u0413"); LITERARY_TO_TRADITIONAL.put("g", "\u0433");
        LITERARY_TO_TRADITIONAL.put("D", "\u0414"); LITERARY_TO_TRADITIONAL.put("d", "\u0434");
        LITERARY_TO_TRADITIONAL.put("E", "\u0415"); LITERARY_TO_TRADITIONAL.put("e", "\u0435");
        LITERARY_TO_TRADITIONAL.put("J", "\u0416"); LITERARY_TO_TRADITIONAL.put("j", "\u0436");
        LITERARY_TO_TRADITIONAL.put("Z", "\u0417"); LITERARY_TO_TRADITIONAL.put("z", "\u0437");
        LITERARY_TO_TRADITIONAL.put("I", "\u042A"); LITERARY_TO_TRADITIONAL.put("i", "\u044A");  // I → Ъ
        LITERARY_TO_TRADITIONAL.put("Y", "\u0419"); LITERARY_TO_TRADITIONAL.put("y", "\u0439");
        LITERARY_TO_TRADITIONAL.put("K", "\u041A"); LITERARY_TO_TRADITIONAL.put("k", "\u043A");
        LITERARY_TO_TRADITIONAL.put("Q", "\u049A"); LITERARY_TO_TRADITIONAL.put("q", "\u049B");
        LITERARY_TO_TRADITIONAL.put("L", "\u041B"); LITERARY_TO_TRADITIONAL.put("l", "\u043B");
        LITERARY_TO_TRADITIONAL.put("M", "\u041C"); LITERARY_TO_TRADITIONAL.put("m", "\u043C");
        LITERARY_TO_TRADITIONAL.put("N", "\u041D"); LITERARY_TO_TRADITIONAL.put("n", "\u043D");
        LITERARY_TO_TRADITIONAL.put("O", "\u0186"); LITERARY_TO_TRADITIONAL.put("o", "\u0254");  // O → Ɔ / ɔ
        LITERARY_TO_TRADITIONAL.put("P", "\u041F"); LITERARY_TO_TRADITIONAL.put("p", "\u043F");
        LITERARY_TO_TRADITIONAL.put("R", "\u0420"); LITERARY_TO_TRADITIONAL.put("r", "\u0440");
        LITERARY_TO_TRADITIONAL.put("S", "\u0421"); LITERARY_TO_TRADITIONAL.put("s", "\u0441");
        LITERARY_TO_TRADITIONAL.put("T", "\u0422"); LITERARY_TO_TRADITIONAL.put("t", "\u0442");
        LITERARY_TO_TRADITIONAL.put("U", "\u0423"); LITERARY_TO_TRADITIONAL.put("u", "\u0443");
        LITERARY_TO_TRADITIONAL.put("F", "\u0424"); LITERARY_TO_TRADITIONAL.put("f", "\u0444");
        LITERARY_TO_TRADITIONAL.put("X", "\u0425"); LITERARY_TO_TRADITIONAL.put("x", "\u0445");
        LITERARY_TO_TRADITIONAL.put("H", "\u04B2"); LITERARY_TO_TRADITIONAL.put("h", "\u04B3");

        // ===== Anʼanaviy (Kirill) → Yangi transkripsiya (doc1) =====
        TRADITIONAL_TO_NEW.put("\u041D\u0433", "\u01A0");  // Нг → Ƞ
        TRADITIONAL_TO_NEW.put("\u041D\u0413", "\u01A0");  // НГ → Ƞ
        TRADITIONAL_TO_NEW.put("\u043D\u0433", "\u019E");  // нг → ƞ

        TRADITIONAL_TO_NEW.put("\u0410", "A"); TRADITIONAL_TO_NEW.put("\u0430", "a");
        TRADITIONAL_TO_NEW.put("\u0411", "B"); TRADITIONAL_TO_NEW.put("\u0431", "b");
        TRADITIONAL_TO_NEW.put("\u0412", "V"); TRADITIONAL_TO_NEW.put("\u0432", "v");
        TRADITIONAL_TO_NEW.put("\u0413", "G"); TRADITIONAL_TO_NEW.put("\u0433", "g");
        TRADITIONAL_TO_NEW.put("\u0492", "\u0393"); TRADITIONAL_TO_NEW.put("\u0493", "\u03B3");  // Ғ → Γ, ғ → γ
        TRADITIONAL_TO_NEW.put("\u0414", "D"); TRADITIONAL_TO_NEW.put("\u0434", "d");
        TRADITIONAL_TO_NEW.put("\u0415", "E"); TRADITIONAL_TO_NEW.put("\u0435", "e");
        TRADITIONAL_TO_NEW.put("\u0416", "\u01EE"); TRADITIONAL_TO_NEW.put("\u0436", "\u01EF");  // Ж → Ǯ, ж → ǯ
        TRADITIONAL_TO_NEW.put("\u0417", "Z"); TRADITIONAL_TO_NEW.put("\u0437", "z");
        TRADITIONAL_TO_NEW.put("\u0418", "I:"); TRADITIONAL_TO_NEW.put("\u0438", "i:");
        TRADITIONAL_TO_NEW.put("\u042B", "\u00CF:"); TRADITIONAL_TO_NEW.put("\u044B", "\u00EF:");  // Ы → Ï:, ы → ï:
        TRADITIONAL_TO_NEW.put("\u0419", "J"); TRADITIONAL_TO_NEW.put("\u0439", "j");
        TRADITIONAL_TO_NEW.put("\u041A", "K"); TRADITIONAL_TO_NEW.put("\u043A", "k");
        TRADITIONAL_TO_NEW.put("\u049A", "Q"); TRADITIONAL_TO_NEW.put("\u049B", "q");
        TRADITIONAL_TO_NEW.put("\u041B", "L"); TRADITIONAL_TO_NEW.put("\u043B", "l");
        TRADITIONAL_TO_NEW.put("\u041C", "M"); TRADITIONAL_TO_NEW.put("\u043C", "m");
        TRADITIONAL_TO_NEW.put("\u041D", "N"); TRADITIONAL_TO_NEW.put("\u043D", "n");
        TRADITIONAL_TO_NEW.put("\u041E", "O"); TRADITIONAL_TO_NEW.put("\u043E", "o");
        TRADITIONAL_TO_NEW.put("\u04E8", "\u00D6"); TRADITIONAL_TO_NEW.put("\u04E9", "\u00F6");  // Ө → Ö
        TRADITIONAL_TO_NEW.put("\u041F", "P"); TRADITIONAL_TO_NEW.put("\u043F", "p");
        TRADITIONAL_TO_NEW.put("\u0420", "R"); TRADITIONAL_TO_NEW.put("\u0440", "r");
        TRADITIONAL_TO_NEW.put("\u0421", "S"); TRADITIONAL_TO_NEW.put("\u0441", "s");
        TRADITIONAL_TO_NEW.put("\u0422", "T"); TRADITIONAL_TO_NEW.put("\u0442", "t");
        TRADITIONAL_TO_NEW.put("\u0423", "U"); TRADITIONAL_TO_NEW.put("\u0443", "u");
        TRADITIONAL_TO_NEW.put("\u04AE", "\u00DC"); TRADITIONAL_TO_NEW.put("\u04AF", "\u00FC");  // Ү → Ü
        TRADITIONAL_TO_NEW.put("\u0424", "F"); TRADITIONAL_TO_NEW.put("\u0444", "f");
        TRADITIONAL_TO_NEW.put("\u0425", "X"); TRADITIONAL_TO_NEW.put("\u0445", "x");
        TRADITIONAL_TO_NEW.put("\u04B2", "H"); TRADITIONAL_TO_NEW.put("\u04B3", "h");
        TRADITIONAL_TO_NEW.put("\u0427", "\u010C"); TRADITIONAL_TO_NEW.put("\u0447", "\u010D");  // Ч → Č
        TRADITIONAL_TO_NEW.put("\u0428", "\u0160"); TRADITIONAL_TO_NEW.put("\u0448", "\u0161");  // Ш → Š
        TRADITIONAL_TO_NEW.put("\u042A", "i"); TRADITIONAL_TO_NEW.put("\u044A", "i");           // Ъ → i
        TRADITIONAL_TO_NEW.put("\u042C", "\u00EF"); TRADITIONAL_TO_NEW.put("\u044C", "\u00EF"); // Ь → ï
        TRADITIONAL_TO_NEW.put("\u042D", "E"); TRADITIONAL_TO_NEW.put("\u044D", "e");           // Э → E
    }

    /**
     * Gʻ/Oʻ kabi harflar uchun 4 xil apostrof variantini qo'shadi (ʻ ' ' ').
     */
    private static void putApostrophePairs(Map<String, String> map, String base, String target) {
        map.put(base + "\u02BB", target);  // ʻ (standart)
        map.put(base + "\u2019", target);  // ' (right single quote)
        map.put(base + "\u2018", target);  // ' (left single quote)
        map.put(base + "'", target);       // ' (plain apostrophe)
    }

    public String convert(String text, ConversionType type) {
        return switch (type) {
            case LITERARY_TO_NEW -> applyMapping(text, LITERARY_TO_NEW);
            case LITERARY_TO_TRADITIONAL -> applyMapping(text, LITERARY_TO_TRADITIONAL);
            case TRADITIONAL_TO_NEW -> applyMapping(text, TRADITIONAL_TO_NEW);
        };
    }

    private String applyMapping(String text, Map<String, String> mapping) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            // 2 ta harfli birikmalar (Gʻ, Oʻ, Ng, Ch, Sh, Нг) birinchi
            if (i + 1 < text.length()) {
                String twoChar = text.substring(i, i + 2);
                if (mapping.containsKey(twoChar)) {
                    result.append(mapping.get(twoChar));
                    i += 2;
                    continue;
                }
            }

            String oneChar = String.valueOf(text.charAt(i));
            if (mapping.containsKey(oneChar)) {
                result.append(mapping.get(oneChar));
            } else {
                result.append(text.charAt(i));
            }
            i++;
        }
        return result.toString();
    }
}
