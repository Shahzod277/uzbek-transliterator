package uz.transliterator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_state")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserState {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Convert(converter = ConversionTypeConverter.class)
    @Column(name = "conversion_type")
    private ConversionType conversionType;
}
