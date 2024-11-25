package lee.io.ai.domain.image.service;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.image.entity.Image;
import lee.io.ai.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class ImageCreator {

    private final ImageRepository imageRepository;

    public Image createImage(String imageName, String imageUrl, String action, Character character) {
        Image image = Image.of(imageName, imageUrl, action);
        image.setCharacter(character);
        return imageRepository.save(image);
    }

}
