package lee.io.ai.domain.like.service;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.like.entity.Likes;
import lee.io.ai.domain.like.repository.LikeRepository;
import lee.io.ai.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeCreator {

    private final LikeRepository likeRepository;

    public void createLike(Member member, Character character) {
        Likes likes = Likes.create(member, character);
        likeRepository.save(likes);
    }

}
