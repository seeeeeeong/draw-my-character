package lee.io.ai.domain.like.service;

import lee.io.ai.domain.like.entity.Likes;
import lee.io.ai.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeRemover {

    private final LikeRepository likeRepository;

    public void deleteLike(Likes likes) {
        likeRepository.delete(likes);
    }
}
