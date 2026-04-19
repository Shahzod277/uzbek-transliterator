package uz.transliterator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Component
@EnableScheduling
public class KeepAliveScheduler {

    @Scheduled(fixedRate = 600000) // har 10 daqiqada
    public void keepAlive() {
        try {
            URL url = new URL("https://uzbek-transliterator.onrender.com/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            int code = conn.getResponseCode();
            log.info("Keep-alive ping: {}", code);
            conn.disconnect();
        } catch (Exception e) {
            log.warn("Keep-alive ping failed: {}", e.getMessage());
        }
    }
}
