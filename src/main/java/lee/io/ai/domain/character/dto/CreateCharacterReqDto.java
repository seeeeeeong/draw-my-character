package lee.io.ai.domain.character.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCharacterReqDto(
        @NotBlank(message = "캐릭터 이름이 비어있습니다.")
        String characterName,
        @NotBlank(message = "url이 비어있습니다.")
        String characterImageUrl,
        @NotBlank(message = "특징이 비어있습니다.")
        @Size(max = 1500, message = "최대 입력 길이(1500자)를 초과하였습니다.")
        String features,

        @NotNull(message = "익명 여부가 입력되지 않았습니다.")
        boolean isAnonymous
){
}
