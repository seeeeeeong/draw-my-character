package lee.io.ai.domain.ai.controller;

import jakarta.validation.Valid;
import lee.io.ai.domain.ai.service.AiService;
import lee.io.ai.domain.ai.dto.AnalyzeImageReqDto;
import lee.io.ai.domain.ai.dto.AnalyzeImageResDto;
import lee.io.ai.domain.ai.dto.GenerateImageReqDto;
import lee.io.ai.domain.ai.dto.GenerateImageResDto;
import lee.io.ai.domain.ai.dto.GenerateImageWithActionReqDto;
import lee.io.ai.domain.ai.dto.GenerateImageWithActionResDto;
import lee.io.ai.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/analyze")
    public ApiResponse<AnalyzeImageResDto> analyzeImage(@RequestBody @Valid AnalyzeImageReqDto request) {
        return ApiResponse.success(aiService.analyzeImage(request));
    }

    @PostMapping("/generate")
    public ApiResponse<GenerateImageResDto> generateImage(@RequestBody @Valid GenerateImageReqDto request) {
        return ApiResponse.success(aiService.generateImage(request));
    }

    @PostMapping("/generate-with-action")
    public ApiResponse<GenerateImageWithActionResDto> generateImageWithAction(@AuthenticationPrincipal Long memberId,
                                                                              @RequestBody @Valid GenerateImageWithActionReqDto request) {
        return ApiResponse.success(aiService.generateImageWithAction(memberId, request));
    }
}
