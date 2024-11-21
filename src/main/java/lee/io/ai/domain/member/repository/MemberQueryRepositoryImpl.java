package lee.io.ai.domain.member.repository;

import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.enums.Provider;
import lee.io.ai.global.repository.Querydsl5RepositorySupport;

import java.util.Optional;

import static lee.io.ai.domain.member.entity.QMember.member;
import static lee.io.ai.global.jwt.entity.QToken.token;

public class MemberQueryRepositoryImpl extends Querydsl5RepositorySupport implements MemberQueryRepository {

    public MemberQueryRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public Optional<Member> findByProviderUidAndProvider(String providerUid, Provider provider) {
        return Optional.ofNullable(
                selectFrom(member)
                        .where(member.providerUid.eq(providerUid), member.provider.eq(provider))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Member> findByRefreshToken(String refreshToken) {
        return Optional.ofNullable(
                selectFrom(member)
                        .join(token).on(member.id.eq(token.memberId))
                        .where(token.refreshToken.eq(refreshToken))
                        .fetchOne()
        );
    }
}
