package lee.io.ai.domain.image.dto;

import lombok.Getter;

import java.net.URL;

public record GetImageFeaturesReqDto(
        URL imageUrl
) {
}
