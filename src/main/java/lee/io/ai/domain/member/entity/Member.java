package lee.io.ai.domain.member.entity;

import jakarta.persistence.*;
import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.member.enums.Provider;
import lee.io.ai.domain.member.enums.Role;
import lee.io.ai.global.entity.BaseTimeEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> characters = new ArrayList<>();

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
