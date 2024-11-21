package lee.io.ai.domain.member.service;

import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.enums.Provider;
import lee.io.ai.domain.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreator {

    private final MemberRepository memberRepository;

    public Member createMember(String email, String providerUid, Provider provider) {
        Member member = Member.of(email, providerUid, provider);
        return memberRepository.save(member);
    }

}
