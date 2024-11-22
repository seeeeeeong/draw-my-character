package lee.io.ai.domain.image.dto;


import java.net.URL;

public record GetImageFeaturesResDto (
        URL imageUrl,
        String features
) {
    public static GetImageFeaturesResDto of(
            final URL imageUrl,
            String features
    ) {
        return new GetImageFeaturesResDto(
                imageUrl,
                features
        );
    }
}
