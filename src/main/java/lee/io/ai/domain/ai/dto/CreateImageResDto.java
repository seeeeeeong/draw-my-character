package lee.io.ai.domain.ai.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateImageResDto {

    private List<String> imageUrls;
    private String features;

    public static CreateImageResDto of(List<String> imageUrls, String features) {
        return CreateImageResDto.builder()
                .imageUrls(imageUrls)
                .features(features)
                .build();
    }
}
