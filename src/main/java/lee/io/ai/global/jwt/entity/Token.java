package lee.io.ai.global.jwt.entity;

import jakarta.persistence.*;
import lee.io.ai.global.entity.BaseTimeEntity;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @Column(nullable = false)
    private String refreshToken;

    @Builder
    public Token(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public static Token of(
            final Long memberId,
            final String refreshToken
    ) {
        return Token.builder()
                .memberId(memberId)
                .refreshToken(refreshToken)
                .build();
    }
}
