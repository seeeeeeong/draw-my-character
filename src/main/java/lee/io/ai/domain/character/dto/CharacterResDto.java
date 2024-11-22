package lee.io.ai.domain.character.dto;

import lee.io.ai.domain.character.entity.Character;

public record CharacterResDto(
        Long characterId,
        String generationId,
        String characterName,
        String characterImage,
        String features
) {
    public static CharacterResDto from(
            final Character character
    ) {
        return new CharacterResDto(
                character.getId(),
                character.getGenerationId(),
                character.getCharacterName(),
                character.getCharacterImage(),
                character.getFeatures()
        );
    }
}
