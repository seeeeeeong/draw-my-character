package lee.io.ai.domain.image.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public record CreateImageResDto (
        List<String> imageUrls,
        String features
){
    public static CreateImageResDto of(
            final List<String> imageUrls,
            final String features
    ) {
        return new CreateImageResDto(
                imageUrls,
                features
        );
    }
}
