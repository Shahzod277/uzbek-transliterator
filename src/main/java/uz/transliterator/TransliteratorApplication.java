package uz.transliterator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
public class TransliteratorApplication {

    public static void main(String[] args) {
        applyRenderDatabaseUrl();
        SpringApplication.run(TransliteratorApplication.class, args);
    }

    // Render beradigan postgresql://user:pass@host:port/db ni JDBC formatiga o'giradi
    private static void applyRenderDatabaseUrl() {
        String url = System.getenv("DATABASE_URL");
        if (url == null || !(url.startsWith("postgres://") || url.startsWith("postgresql://"))) {
            return;
        }
        URI uri = URI.create(url);
        String port = uri.getPort() > 0 ? ":" + uri.getPort() : "";
        System.setProperty("spring.datasource.url",
                "jdbc:postgresql://" + uri.getHost() + port + uri.getPath());
        String userInfo = uri.getUserInfo();
        if (userInfo != null) {
            String[] parts = userInfo.split(":", 2);
            System.setProperty("spring.datasource.username", parts[0]);
            if (parts.length > 1) {
                System.setProperty("spring.datasource.password", parts[1]);
            }
        }
    }
}
