package lee.io.ai.domain.character.repository;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.global.repository.Querydsl5RepositorySupport;

import java.util.Optional;

import static lee.io.ai.domain.character.entity.QCharacter.character;
import static lee.io.ai.domain.image.entity.QImage.image;

public class CharacterQueryRepositoryImpl extends Querydsl5RepositorySupport implements CharacterQueryRepository {

    public CharacterQueryRepositoryImpl(){
        super(Character.class);
    }

    @Override
    public Optional<Character> findByCharacterId(Long characterId) {
        return Optional.ofNullable(
                selectFrom(character)
                        .leftJoin(character.images, image).fetchJoin()
                        .where(character.id.eq(characterId))
                        .fetchOne()
        );
    }
}
