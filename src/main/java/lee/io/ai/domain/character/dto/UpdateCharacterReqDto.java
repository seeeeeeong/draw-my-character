package lee.io.ai.domain.character.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCharacterReqDto(
        @NotBlank(message = "캐릭터 이름이 비어있습니다.")
        String characterName
) {

}
