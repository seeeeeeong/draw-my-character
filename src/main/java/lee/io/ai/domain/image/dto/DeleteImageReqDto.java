package lee.io.ai.domain.image.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DeleteImageReqDto(
        @NotBlank(message = "이미지 아이디가 비어있습니다..")
        List<Long> imageIds
) {

}
