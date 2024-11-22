package lee.io.ai.domain.member.service;

import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberCreator memberCreator;
    private final MemberRetriever memberRetriever;

    public Member createMember(String email, String providerUid, Provider provider) {
        return memberCreator.createMember(email, providerUid, provider);
    }

    public Member getById(Long memberId) {
        return memberRetriever.getById(memberId);
    }

    public Member getByProviderUid(String providerUid, Provider provider) {
        return memberRetriever.getByProviderUid(providerUid, provider);
    }

    public Member getByRefreshToken(String refreshToken) {
        return memberRetriever.getByRefreshToken(refreshToken);
    }

}
