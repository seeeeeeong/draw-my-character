package lee.io.ai.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lee.io.ai.domain.auth.service.AuthService;
import lee.io.ai.global.jwt.dto.TokenDto;
import lee.io.ai.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/oauth2/code/{provider}")
    public ApiResponse<TokenDto> login(@PathVariable(name = "provider") String provider,
                                       @RequestParam(name = "code") String code) throws JsonProcessingException {
        return ApiResponse.success(authService.login(provider, code));
    }
}
