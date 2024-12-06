package lee.io.ai.domain.character.repository;

import com.querydsl.core.types.Projections;
import lee.io.ai.domain.character.dto.GetCharactersListResDto;
import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.global.repository.Querydsl5RepositorySupport;

import java.util.List;
import java.util.Optional;

import static lee.io.ai.domain.character.entity.QCharacter.character;
import static lee.io.ai.domain.image.entity.QImage.image;
import static lee.io.ai.domain.like.entity.QLikes.likes;

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
                        .distinct()
                        .fetchOne()
        );
    }

    @Override
    public List<GetCharactersListResDto> findAllCharacters() {
        return select(
                Projections.fields(
                        GetCharactersListResDto.class,
                        character.id.as("characterId"),
                        character.characterName,
                        character.characterImageUrl,
                        character.createdAt,
                        likes.id.count().as("likeCount")
                ))
                .from(character)
                .leftJoin(likes).on(character.id.eq(likes.character.id))
                .groupBy(character.id, character.characterName, character.createdAt)
                .orderBy(likes.id.count().desc())
                .limit(30)
                .fetch();
    }

    @Override
    public List<GetCharactersListResDto> findCharactersByMember(Member member) {
        return select(
                Projections.fields(
                        GetCharactersListResDto.class,
                        character.id.as("characterId"),
                        character.characterName,
                        character.characterImageUrl,
                        character.createdAt,
                        likes.id.count().as("likeCount")
                ))
                .from(character)
                .leftJoin(likes).on(character.id.eq(likes.character.id))
                .where(character.member.eq(member))
                .groupBy(character.id, character.characterName, character.createdAt)
                .orderBy(likes.id.count().desc())
                .limit(30)
                .fetch();
    }
}
