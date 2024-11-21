package lee.io.ai.domain.member.repository;

import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.enums.Provider;

import java.util.Optional;

public interface MemberQueryRepository {

    Optional<Member> findByProviderUidAndProvider(String providerUid, Provider provider);

    Optional<Member> findByRefreshToken(String refreshToken);

}
