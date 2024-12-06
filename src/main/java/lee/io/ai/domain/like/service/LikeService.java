package lee.io.ai.domain.like.service;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.character.service.CharacterRetriever;
import lee.io.ai.domain.like.dto.LikeReqDto;
import lee.io.ai.domain.like.entity.Likes;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.service.MemberRetriever;
import lee.io.ai.global.exception.BusinessException;
import lee.io.ai.global.exception.ErrorCode;
import lee.io.ai.global.exception.notfound.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRetriever likeRetriever;
    private final LikeCreator likeCreator;
    private final LikeRemover likeRemover;

    private final CharacterRetriever characterRetriever;
    private final MemberRetriever memberRetriever;

    @Transactional
    public Boolean createLike(Long memberId, LikeReqDto request) {
        Member member = memberRetriever.getMemberByMemberId(memberId);
        Character character = characterRetriever.getCharacterByCharacterId(request.characterId());

        Optional<Likes> optionalLike = likeRetriever.getLikeByMemberAndCharacter(member, character);

        if (optionalLike.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_LIKED);
        }

        likeCreator.createLike(member, character);
        return true;
    }

    @Transactional
    public Boolean deleteLike(Long memberId, LikeReqDto request) {
        Member member = memberRetriever.getMemberByMemberId(memberId);
        Character character = characterRetriever.getCharacterByCharacterId(request.characterId());

        Likes likes = likeRetriever.getLikeByMemberAndCharacter(member, character)
                        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.LIKE_NOT_FOUND));

        likeRemover.deleteLike(likes);
        return true;
    }
}
