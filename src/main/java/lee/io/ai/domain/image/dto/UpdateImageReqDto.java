package lee.io.ai.domain.image.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateImageReqDto(
        @NotBlank(message = "이미지 이름이 비어있습니다.")
        String imageName
){

}