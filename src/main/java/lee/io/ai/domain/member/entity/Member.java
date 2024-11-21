package lee.io.ai.domain.member.entity;

import jakarta.persistence.*;
import lee.io.ai.domain.member.enums.Provider;
import lee.io.ai.domain.member.enums.Role;
import lee.io.ai.global.entity.BaseTimeEntity;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String providerUid;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member of(
            final String email,
            final String providerUid,
            final Provider provider
    ) {
        return Member.builder()
                .email(email)
                .providerUid(providerUid)
                .provider(provider)
                .role(Role.USER)
                .build();
    }

}
