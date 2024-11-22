package lee.io.ai.domain.ai.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetImageFeaturesResDto {

    private String features;

    public static GetImageFeaturesResDto of(String features) {
        return GetImageFeaturesResDto.builder()
                .features(features)
                .build();
    }
}
