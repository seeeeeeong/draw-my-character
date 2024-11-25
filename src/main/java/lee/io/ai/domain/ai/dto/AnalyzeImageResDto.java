package lee.io.ai.domain.ai.dto;


public record AnalyzeImageResDto(
        String imageUrl,
        String features
) {
    public static AnalyzeImageResDto of(
            final String imageUrl,
            final String features
    ) {
        return new AnalyzeImageResDto(
                imageUrl,
                features
        );
    }
}
