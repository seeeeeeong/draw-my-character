package lee.io.ai.domain.image.repository;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.image.entity.Image;
import lee.io.ai.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByCharacterId(Long characterId);
}
