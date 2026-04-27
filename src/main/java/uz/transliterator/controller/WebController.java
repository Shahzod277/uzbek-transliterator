package uz.transliterator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.transliterator.entity.ConversionType;
import uz.transliterator.service.TransliterationService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final TransliterationService transliterationService;

    @GetMapping("/translate")
    public String translatePage() {
        return "translate";
    }

    @PostMapping("/api/translate")
    @ResponseBody
    public ResponseEntity<Map<String, String>> translate(@RequestBody Map<String, String> request) {
        String text = request.getOrDefault("text", "");
        String type = request.getOrDefault("type", "LITERARY_TO_NEW");

        if (text.isBlank()) {
            return ResponseEntity.ok(Map.of("result", ""));
        }

        String result = transliterationService.convert(text, ConversionType.valueOf(type));
        return ResponseEntity.ok(Map.of("result", result));
    }
}
