package lee.io.ai.domain.image.entity;

import jakarta.persistence.*;
import lee.io.ai.domain.character.entity.CharacterImage;
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

    private String image;
    private String description;

}
