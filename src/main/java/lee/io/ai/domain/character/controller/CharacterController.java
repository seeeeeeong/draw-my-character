package lee.io.ai.domain.character.controller;

import jakarta.validation.Valid;
import lee.io.ai.domain.character.dto.*;
import lee.io.ai.domain.character.service.CharacterService;
import lee.io.ai.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    public ApiResponse<CreateCharacterResDto> createCharacter(@AuthenticationPrincipal Long memberId,
                                                              @RequestBody @Valid CreateCharacterReqDto request) {
        return ApiResponse.created(characterService.createCharacter(memberId, request));
    }

    @GetMapping
    public ApiResponse<List<GetCharactersListResDto>> getCharacterList(@AuthenticationPrincipal Long memberId) {
        return ApiResponse.success(characterService.getCharacterList(memberId));
    }

    @GetMapping("/{characterId}")
    public ApiResponse<GetCharacterDetailResDto> getCharacterDetail(@AuthenticationPrincipal Long memberId,
                                                                    @PathVariable("characterId") Long characterId) {
        return ApiResponse.success((characterService.getCharacterDetail(memberId, characterId)));
    }

    @PutMapping("/{characterId}")
    public ApiResponse<Boolean> updateCharacter(@AuthenticationPrincipal Long memberId,
                                                 @PathVariable("characterId") Long characterId,
                                                 @RequestBody @Valid UpdateCharacterReqDto request) {
        return ApiResponse.success(characterService.updateCharacter(memberId, characterId, request));
    }

    @DeleteMapping
    public ApiResponse<Boolean> deleteCharacter(@AuthenticationPrincipal Long memberId,
                                                @RequestBody @Valid DeleteCharacterReqDto request) {
        return ApiResponse.success(characterService.deleteCharacter(memberId, request));
    }

}
