package lee.io.ai.domain.image.dto;

import lee.io.ai.domain.image.entity.Image;

public record CreateCharacterImageResDto(
        Long imageId,
        String imageName,
        String imageUrl,
        String action
) {
    public static CreateCharacterImageResDto from(
            final Image image
            ) {
        return new CreateCharacterImageResDto(
                image.getId(),
                image.getImageName(),
                image.getImageUrl(),
                image.getAction()
        );
    }
}
