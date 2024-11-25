package lee.io.ai.domain.character.repository;

import lee.io.ai.domain.character.entity.Character;

import java.util.Optional;

public interface CharacterQueryRepository {

    Optional<Character> findByCharacterId(Long characterId);

}
