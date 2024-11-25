package lee.io.ai.domain.image.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCharacterImageReqDto(
        @NotBlank(message = "이미지 이름이 비어있습니다.")
        String imageName,
        @NotBlank(message = "url이 비어있습니다.")
        String imageUrl,

        @NotBlank(message = "action이 비어있습니다.")
        String action
) {

}
