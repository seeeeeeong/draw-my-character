package lee.io.ai.domain.image.controller;

import jakarta.validation.Valid;
import lee.io.ai.domain.image.dto.*;
import lee.io.ai.domain.image.service.ImageService;
import lee.io.ai.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/characters/{characterId}/images")
    public ApiResponse<CreateCharacterImageResDto> createCharacterImage(@AuthenticationPrincipal Long memberId,
                                                                        @PathVariable("characterId") Long characterId,
                                                                        @RequestBody @Valid CreateCharacterImageReqDto request) {
        return ApiResponse.created(imageService.createCharacterImage(memberId, characterId, request));
    }

    @PutMapping("/characters/{characterId}/images/{imageId}")
    public ApiResponse<Boolean> updateCharacterImage(@AuthenticationPrincipal Long memberId,
                                                  @PathVariable("characterId") Long characterId,
                                                  @PathVariable("imageId") Long imageId,
                                                  @RequestBody @Valid UpdateImageReqDto request) {
        return ApiResponse.success(imageService.updateCharacterImage(memberId, characterId, imageId, request));
    }

    @DeleteMapping("/characters/{characterId}/images")
    public ApiResponse<Boolean> deleteCharacterImage(@AuthenticationPrincipal Long memberId,
                                                     @PathVariable("characterId") Long characterId,
                                                     @RequestBody @Valid DeleteImageReqDto request) {
        return ApiResponse.success(imageService.deleteImage(memberId, characterId, request));
    }

}
