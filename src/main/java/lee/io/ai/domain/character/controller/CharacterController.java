package lee.io.ai.domain.character.controller;

import jakarta.validation.Valid;
import lee.io.ai.domain.character.dto.CharacterReqDto;
import lee.io.ai.domain.character.dto.CharacterResDto;
import lee.io.ai.domain.character.service.CharacterService;
import lee.io.ai.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    public ApiResponse<CharacterResDto> createCharacter(@AuthenticationPrincipal Long memberId,
                                                        @RequestBody @Valid CharacterReqDto request) {
        return ApiResponse.created(characterService.createCharacter(memberId, request));
    }

}
