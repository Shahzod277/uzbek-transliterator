package uz.transliterator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversion_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "username")
    private String username;

    @Column(name = "original_text", columnDefinition = "TEXT", nullable = false)
    private String originalText;

    @Column(name = "converted_text", columnDefinition = "TEXT", nullable = false)
    private String convertedText;

    @Convert(converter = ConversionTypeConverter.class)
    @Column(name = "conversion_type", nullable = false)
    private ConversionType conversionType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
