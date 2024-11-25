package lee.io.ai.domain.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record GenerateImageReqDto(
        @NotBlank(message = "특징이 비어있습니다.")
        String features,
        @NotNull(message = "이미지 개수가 비어있습니다.")
        @Range(min = 1, max = 5)
        int numberOfImages
) {
}
