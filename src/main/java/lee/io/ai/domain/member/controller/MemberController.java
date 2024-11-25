package lee.io.ai.domain.member.controller;

import lee.io.ai.domain.character.dto.GetCharactersListResDto;
import lee.io.ai.domain.member.service.MemberService;
import lee.io.ai.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/characters")
    public ApiResponse<List<GetCharactersListResDto>> getCharacterListByMember(@AuthenticationPrincipal Long memberId) {
        return ApiResponse.success(memberService.getCharacterListByMember(memberId));
    }

}
