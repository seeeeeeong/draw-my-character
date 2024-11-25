package lee.io.ai.domain.character.service;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.character.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CharacterRemover {

    private final CharacterRepository characterRepository;

    public void deleteAll(List<Character> characters) {
        characterRepository.deleteAll(characters);
    }
}
