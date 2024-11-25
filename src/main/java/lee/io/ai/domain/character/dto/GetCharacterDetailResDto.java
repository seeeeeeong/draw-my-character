package lee.io.ai.domain.character.dto;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.image.entity.Image;

import java.util.List;

public record GetCharacterDetailResDto(
        Long id,
        String characterName,
        String characterImageUrl,
        String features,
        boolean isMyCharacter,
        List<ImageResDto> images
) {
    public static GetCharacterDetailResDto of(
            final Character character,
            final Long memberId
    ) {
        return new GetCharacterDetailResDto(
                character.getId(),
                character.getCharacterName(),
                character.getCharacterImageUrl(),
                character.getFeatures(),
                character.getMember().getId().equals(memberId),
                character.getImages().stream()
                        .map(ImageResDto::from)
                        .toList()
        );
    }

    public record ImageResDto(
            Long imageId,
            String imageName,
            String imageUrl,
            String action
    ) {
        public static ImageResDto from(final Image image) {
            return new ImageResDto(
                    image.getId(),
                    image.getImageName(),
                    image.getImageUrl(),
                    image.getAction()
            );
        }
    }
}
