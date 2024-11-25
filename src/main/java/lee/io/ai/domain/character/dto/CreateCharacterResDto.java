package lee.io.ai.domain.character.dto;

import lee.io.ai.domain.character.entity.Character;

public record CreateCharacterResDto(
        Long characterId,
        String characterName,
        String characterImageUrl,
        String features
) {
    public static CreateCharacterResDto from(
            final Character character
    ) {
        return new CreateCharacterResDto(
                character.getId(),
                character.getCharacterName(),
                character.getCharacterImageUrl(),
                character.getFeatures()
        );
    }
}
