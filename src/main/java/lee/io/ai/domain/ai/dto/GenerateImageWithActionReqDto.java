package lee.io.ai.domain.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record GenerateImageWithActionReqDto(
        @NotBlank(message = "캐릭타 아이디가 비어있습니다.")
        Long characterId,
        @NotBlank(message = "action 비어있습니다.")
        String action,
        @NotNull(message = "이미지 개수가 비어있습니다.")
        @Range(min = 1, max = 5)
        int numberOfImages
) {
}
