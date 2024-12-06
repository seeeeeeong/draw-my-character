package lee.io.ai.domain.like.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LikeReqDto(
        @NotNull(message = "캐릭터 아이디가 비어있습니다.")
        Long characterId
) {
}
