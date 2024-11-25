package lee.io.ai.domain.character.entity;

import jakarta.persistence.*;
import lee.io.ai.domain.image.entity.Image;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.global.entity.BaseTimeEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Character extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String characterName;

    @Column(name = "character_image", length = 2000)
    private String characterImageUrl;

    @Column(name = "features", length = 2000)
    private String features;

    private boolean anonymous;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public static Character create(
            final String characterName,
            final String characterImageUrl,
            final String features,
            final boolean anonymous,
            final Member member
    ) {
        return Character
                .builder()
                .characterName(characterName)
                .characterImageUrl(characterImageUrl)
                .features(features)
                .anonymous(anonymous)
                .member(member)
                .build();
    }

    public void updateCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
