package lee.io.ai.domain.character.service;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.character.repository.CharacterRepository;
import lee.io.ai.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class CharacterCreator {

    private final CharacterRepository characterRepository;

    public Character createCharacter(String characterName, String characterImage, String features, boolean isAnonymous, Member member) {
        Character character = Character.create(characterName, characterImage, features, isAnonymous, member);
        return characterRepository.save(character);
    }


}
