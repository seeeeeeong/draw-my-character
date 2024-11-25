package lee.io.ai.domain.image.service;

import lee.io.ai.domain.image.entity.Image;
import lee.io.ai.domain.image.repository.ImageRepository;
import lee.io.ai.global.exception.ErrorCode;
import lee.io.ai.global.exception.notfound.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageRetriever {

    private final ImageRepository imageRepository;

    public Image getImagesByImageId(Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.IMAGE_NOT_FOUND));
    }

}
