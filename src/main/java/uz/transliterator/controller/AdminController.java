package uz.transliterator.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.transliterator.entity.ConversionHistory;
import uz.transliterator.entity.ConversionType;
import uz.transliterator.repository.BotUserRepository;
import uz.transliterator.repository.ConversionHistoryRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BotUserRepository botUserRepository;
    private final ConversionHistoryRepository conversionHistoryRepository;

    @Value("${admin.password}")
    private String adminPassword;

    private boolean isAuthenticated(HttpSession session) {
        return Boolean.TRUE.equals(session.getAttribute("admin"));
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String password, HttpSession session) {
        if (adminPassword.equals(password)) {
            session.setAttribute("admin", true);
            return "redirect:/admin/dashboard";
        }
        return "redirect:/admin/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping
    public String index(HttpSession session) {
        if (!isAuthenticated(session)) return "redirect:/admin/login";
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAuthenticated(session)) return "redirect:/admin/login";

        long totalUsers = botUserRepository.count();
        long totalConversions = conversionHistoryRepository.count();
        long todayConversions = conversionHistoryRepository.countSince(LocalDate.now().atStartOfDay());

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalConversions", totalConversions);
        model.addAttribute("todayConversions", todayConversions);

        Map<String, Long> typeCounts = new LinkedHashMap<>();
        for (ConversionType t : ConversionType.values()) typeCounts.put(t.name(), 0L);
        for (Object[] row : conversionHistoryRepository.countGroupByType()) {
            typeCounts.put(((ConversionType) row[0]).name(), (Long) row[1]);
        }
        model.addAttribute("literaryToNew", typeCounts.getOrDefault("LITERARY_TO_NEW", 0L));
        model.addAttribute("literaryToTraditional", typeCounts.getOrDefault("LITERARY_TO_TRADITIONAL", 0L));
        model.addAttribute("traditionalToNew", typeCounts.getOrDefault("TRADITIONAL_TO_NEW", 0L));

        LocalDate weekAgo = LocalDate.now().minusDays(6);
        Map<LocalDate, Long> dailyMap = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) dailyMap.put(LocalDate.now().minusDays(i), 0L);
        for (Object[] row : conversionHistoryRepository.countDailySince(weekAgo.atStartOfDay())) {
            LocalDate date = ((Date) row[0]).toLocalDate();
            dailyMap.put(date, (Long) row[1]);
        }
        List<String> chartLabels = new ArrayList<>();
        List<Long> chartData = new ArrayList<>();
        dailyMap.forEach((date, count) -> {
            chartLabels.add(date.getDayOfMonth() + "/" + String.format("%02d", date.getMonthValue()));
            chartData.add(count);
        });
        model.addAttribute("chartLabels", chartLabels);
        model.addAttribute("chartData", chartData);

        model.addAttribute("topUsers",
                conversionHistoryRepository.findTopActiveUsers(PageRequest.of(0, 10)));

        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(HttpSession session, Model model) {
        if (!isAuthenticated(session)) return "redirect:/admin/login";

        model.addAttribute("users", botUserRepository.findAllByOrderByRegisteredAtDesc());

        Map<Long, Long> conversionCounts = conversionHistoryRepository.countPerUser().stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));
        model.addAttribute("conversionCounts", conversionCounts);

        return "admin/users";
    }

    @GetMapping("/history")
    public String history(
            HttpSession session,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String type,
            Model model) {
        if (!isAuthenticated(session)) return "redirect:/admin/login";

        Page<ConversionHistory> historyPage;
        if (type != null && !type.isEmpty()) {
            historyPage = conversionHistoryRepository.findByConversionTypeOrderByCreatedAtDesc(
                    ConversionType.valueOf(type), PageRequest.of(page, 20));
        } else {
            historyPage = conversionHistoryRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, 20));
        }

        model.addAttribute("historyPage", historyPage);
        model.addAttribute("currentType", type);
        model.addAttribute("conversionTypes", ConversionType.values());

        return "admin/history";
    }
}