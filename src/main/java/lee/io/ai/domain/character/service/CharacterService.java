package lee.io.ai.domain.character.service;

import lee.io.ai.domain.character.dto.CharacterReqDto;
import lee.io.ai.domain.character.dto.CharacterResDto;
import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.character.repository.CharacterRepository;
import lee.io.ai.domain.image.service.ImageService;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;

    private final MemberService memberService;
    private final ImageService imageService;

    @Transactional
    public CharacterResDto createCharacter(Long memberId, CharacterReqDto request) {
        Member member = memberService.getById(memberId);
        Character character = Character.create(request.characterName(), request.characterImage(), request.features(), member);
        characterRepository.save(character);
        return CharacterResDto.from(character);
    }

}
