package lee.io.ai.domain.character.dto;

import jakarta.validation.constraints.NotNull;

public record CharacterReqDto (
        @NotNull
        String characterName,
        @NotNull
        String characterImage,
        @NotNull
        String features
){
}
