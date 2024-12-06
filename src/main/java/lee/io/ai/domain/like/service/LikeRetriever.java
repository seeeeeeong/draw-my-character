package lee.io.ai.domain.like.service;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.like.entity.Likes;
import lee.io.ai.domain.like.repository.LikeRepository;
import lee.io.ai.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikeRetriever {

    private final LikeRepository likeRepository;

    public Optional<Likes> getLikeByMemberAndCharacter(Member member, Character character) {
        return likeRepository.findByMemberAndCharacter(member, character);
    }
}
