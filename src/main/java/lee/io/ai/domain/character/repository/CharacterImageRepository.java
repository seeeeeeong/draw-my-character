package lee.io.ai.domain.character.repository;

import lee.io.ai.domain.character.entity.CharacterImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterImageRepository extends JpaRepository<CharacterImage, Long> {
}
