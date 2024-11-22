package lee.io.ai.domain.image.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

public record CreateImageReqDto(
        @NotBlank(message = "특징이 비어있습니다.")
        String features,
        @NotNull(message = "이미지 개수가 비어있습니다.")
        @Range(min = 1, max = 5)
        int numberOfImages
) {
}
