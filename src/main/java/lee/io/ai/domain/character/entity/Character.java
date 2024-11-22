package lee.io.ai.domain.character.entity;

import jakarta.persistence.*;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.global.entity.BaseTimeEntity;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Character extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String generationId;
    private String characterName;
    private String characterImage;
    private String feature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
