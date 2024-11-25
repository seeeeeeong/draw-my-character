package lee.io.ai.domain.character.repository;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long>, CharacterQueryRepository {
    List<Character> findByMember(Member member);
}
