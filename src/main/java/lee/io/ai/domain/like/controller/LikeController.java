package lee.io.ai.domain.like.controller;

import jakarta.validation.Valid;
import lee.io.ai.domain.like.dto.LikeReqDto;
import lee.io.ai.domain.like.service.LikeService;
import lee.io.ai.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ApiResponse<Boolean> createLike(@AuthenticationPrincipal Long memberId,
                                           @RequestBody @Valid LikeReqDto request) {
        return ApiResponse.created(likeService.createLike(memberId, request));
    }

    @DeleteMapping
    public ApiResponse<Boolean> deleteLike(@AuthenticationPrincipal Long memberId,
                                           @RequestBody @Valid LikeReqDto request) {
        return ApiResponse.success(likeService.deleteLike(memberId, request));
    }

}
