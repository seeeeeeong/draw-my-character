package lee.io.ai.domain.character.dto;

import lee.io.ai.domain.character.entity.Character;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GetCharactersListResDto{

        private Long characterId;
        private String characterName;
        private String characterImageUrl;
        private LocalDateTime createdAt;
        private Long likeCount;

}
