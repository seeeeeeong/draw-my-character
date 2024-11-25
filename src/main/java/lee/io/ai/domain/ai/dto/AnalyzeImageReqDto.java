package lee.io.ai.domain.ai.dto;

import jakarta.validation.constraints.NotBlank;

public record AnalyzeImageReqDto(
        @NotBlank(message = "url이 비어있습니다.")
        String imageUrl
) {
}
