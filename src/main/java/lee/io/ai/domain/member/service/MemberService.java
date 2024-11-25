package lee.io.ai.domain.member.service;

import lee.io.ai.domain.character.dto.GetCharactersListResDto;
import lee.io.ai.domain.character.service.CharacterRetriever;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberCreator memberCreator;
    private final MemberRetriever memberRetriever;

    private final CharacterRetriever characterRetriever;

    public Member createMember(String email, String providerUid, Provider provider) {
        return memberCreator.createMember(email, providerUid, provider);
    }

    public Member getByProviderUid(String providerUid, Provider provider) {
        return memberRetriever.getMemberByProviderUid(providerUid, provider);
    }

    public Member getByRefreshToken(String refreshToken) {
        return memberRetriever.getMemberByRefreshToken(refreshToken);
    }

    public List<GetCharactersListResDto> getCharacterListByMember(Long memberId) {
        Member member = memberRetriever.getMemberByMemberId(memberId);
        List<GetCharactersListResDto> characterList = characterRetriever.getCharactersByMember(member);
        return characterList;
    }
}
