package lee.io.ai.domain.character.service;

import lee.io.ai.domain.character.dto.*;
import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.service.MemberRetriever;
import lee.io.ai.global.exception.BusinessException;
import lee.io.ai.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRetriever characterRetriever;
    private final CharacterCreator characterCreator;
    private final CharacterRemover characterRemover;

    private final MemberRetriever memberRetriever;

    @Transactional
    public CreateCharacterResDto createCharacter(Long memberId, CreateCharacterReqDto request) {
        Member member = memberRetriever.getMemberByMemberId(memberId);
        Character character = characterCreator.createCharacter(request.characterName(), request.characterImageUrl(), request.features(), request.isAnonymous(), member);
        return CreateCharacterResDto.from(character);
    }

    public List<GetCharactersListResDto> getCharacterList(Long memberId) {
        return characterRetriever.getAllCharacters();
    }

    public GetCharacterDetailResDto getCharacterDetail(Long memberId, Long characterId) {
        Character character = characterRetriever.getCharacterByCharacterId(characterId);
        return GetCharacterDetailResDto.of(character, memberId);
    }

    @Transactional
    public Boolean updateCharacter(Long memberId, Long characterId, UpdateCharacterReqDto request) {
        Character character = characterRetriever.getCharacterByCharacterId(characterId);

        if (!character.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.CHARACTER_NOT_FOUND);
        }

        character.updateCharacterName(request.characterName());
        return true;
    }

    @Transactional
    public Boolean deleteCharacter(Long memberId, DeleteCharacterReqDto request) {
        List<Character> characters = request.characterIds().stream()
                .map(characterId -> characterRetriever.getCharacterByCharacterId(characterId))
                .collect(Collectors.toList());

        for (Character character : characters) {
            if (!character.getMember().getId().equals(memberId)) {
                throw new BusinessException(ErrorCode.CHARACTER_NOT_FOUND);
            }
        }

        characterRemover.deleteAll(characters);
        return true;
    }



}
