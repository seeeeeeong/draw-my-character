package lee.io.ai.domain.character.dto;

import lee.io.ai.domain.character.entity.Character;

public record GetCharactersListResDto(
        Long characterId,
        String characterName,
        String characterImageUrl,
        boolean isMyCharacter
) {
    public static GetCharactersListResDto from(
            final Character character,
            final Long memberId
    ) {
        return new GetCharactersListResDto(
                character.getId(),
                character.getCharacterName(),
                character.getCharacterImageUrl(),
                character.getMember().getId().equals(memberId)
        );
    }
}
