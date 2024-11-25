package lee.io.ai.domain.image.entity;

import jakarta.persistence.*;
import lee.io.ai.domain.character.entity.Character;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;

    @Column(name = "character_image", length = 2000)
    private String imageUrl;

    private String action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    public static Image of(String imageName, String imageUrl, String action) {
        return Image.builder()
                .imageName(imageName)
                .imageUrl(imageUrl)
                .action(action)
                .build();
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void updateImageName(String imageName) {
        this.imageName = imageName;
    }

}
