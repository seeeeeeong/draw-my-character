package lee.io.ai.domain.like.repository;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.like.entity.Likes;
import lee.io.ai.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByMemberAndCharacter(Member member, Character character);

}
