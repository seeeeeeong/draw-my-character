package lee.io.ai.domain.ai.dto;

import java.util.List;

public record GenerateImageWithActionResDto(
        List<String> imageUrls,
        String action
){
    public static GenerateImageWithActionResDto of(
            final List<String> imageUrls,
            final String action) {
        return new GenerateImageWithActionResDto(
                imageUrls,
                action
        );
    }
}
