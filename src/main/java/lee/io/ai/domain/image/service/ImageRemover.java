package lee.io.ai.domain.image.service;

import lee.io.ai.domain.image.entity.Image;
import lee.io.ai.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class ImageRemover {

    private final ImageRepository imageRepository;

    public void deleteAll(List<Image> images) {
        imageRepository.deleteAll(images);
    }
}
