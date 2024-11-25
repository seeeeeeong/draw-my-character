package lee.io.ai.domain.ai.dto;

import java.util.List;

public record GenerateImageResDto(
        List<String> imageUrls,
        String features
){
    public static GenerateImageResDto of(
            final List<String> imageUrls,
            final String features
    ) {
        return new GenerateImageResDto(
                imageUrls,
                features
        );
    }
}
