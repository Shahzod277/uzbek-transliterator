package uz.transliterator.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ConversionTypeConverter implements AttributeConverter<ConversionType, String> {

    @Override
    public String convertToDatabaseColumn(ConversionType type) {
        return type == null ? null : type.name();
    }

    @Override
    public ConversionType convertToEntityAttribute(String dbValue) {
        if (dbValue == null) return null;
        return switch (dbValue) {
            case "CYRILLIC_TO_TRADITIONAL_LATIN" -> ConversionType.TRADITIONAL_TO_NEW;
            case "NEW_LATIN_TO_TRADITIONAL_LATIN" -> ConversionType.LITERARY_TO_NEW;
            case "CYRILLIC_TO_NEW_LATIN" -> ConversionType.TRADITIONAL_TO_NEW;
            default -> {
                try {
                    yield ConversionType.valueOf(dbValue);
                } catch (IllegalArgumentException e) {
                    yield ConversionType.LITERARY_TO_NEW;
                }
            }
        };
    }
}
