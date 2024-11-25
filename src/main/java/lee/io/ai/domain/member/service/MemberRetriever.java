package lee.io.ai.domain.member.service;

import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.enums.Provider;
import lee.io.ai.domain.member.repository.MemberRepository;
import lee.io.ai.global.exception.ErrorCode;
import lee.io.ai.global.exception.notfound.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRetriever {

    private final MemberRepository memberRepository;

    public Member getMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member getMemberByProviderUid(String providerUid, Provider provider) {
        return memberRepository.findByProviderUidAndProvider(providerUid, provider).orElse(null);
    }

    public Member getMemberByRefreshToken(String refreshToken) {
        return memberRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
