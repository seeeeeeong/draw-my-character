package lee.io.ai.domain.character.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DeleteCharacterReqDto(
        @NotBlank(message = "캐릭터 아이디가 비어있습니다..")
        List<Long> characterIds
) {
}
