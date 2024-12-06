package lee.io.ai.domain.character.repository;

import lee.io.ai.domain.character.dto.GetCharactersListResDto;
import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface CharacterQueryRepository {

    Optional<Character> findByCharacterId(Long characterId);

    List<GetCharactersListResDto> findAllCharacters();

    List<GetCharactersListResDto> findCharactersByMember(Member member);

}
