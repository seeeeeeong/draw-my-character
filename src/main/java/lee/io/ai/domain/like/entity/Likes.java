package lee.io.ai.domain.like.entity;

import jakarta.persistence.*;
import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.global.entity.BaseTimeEntity;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    public static Likes create(
            final Member member,
            final Character character
            ) {
        return Likes.builder()
                .member(member)
                .character(character)
                .build();
    }
}
