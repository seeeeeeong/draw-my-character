package lee.io.ai.domain.image.service;

import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.character.service.CharacterRetriever;
import lee.io.ai.domain.image.dto.*;
import lee.io.ai.domain.image.entity.Image;
import lee.io.ai.domain.member.service.MemberRetriever;
import lee.io.ai.global.exception.BusinessException;
import lee.io.ai.global.exception.ErrorCode;
import lee.io.ai.global.exception.notfound.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageCreator imageCreator;
    private final ImageRetriever imageRetriever;
    private final ImageRemover imageRemover;

    private final CharacterRetriever characterRetriever;

    @Transactional
    public CreateCharacterImageResDto createCharacterImage(Long memberId, Long characterId, CreateCharacterImageReqDto request) {
        Character character = characterRetriever.getCharacterByCharacterId(characterId);

        if (!character.getMember().getId().equals(memberId)) {
            throw new EntityNotFoundException(ErrorCode.CHARACTER_NOT_FOUND);
        }

        Image image = imageCreator.createImage(request.imageName(), request.imageUrl(), request.action(), character);
        return CreateCharacterImageResDto.from(image);
    }

    @Transactional
    public Boolean updateCharacterImage(Long memberId, Long characterId, Long imageId, UpdateImageReqDto request) {
        Character character = characterRetriever.getCharacterByCharacterId(characterId);

        if (!character.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.CHARACTER_NOT_FOUND);
        }

        Image image = imageRetriever.getImagesByImageId(imageId);
        image.updateImageName(request.imageName());
        return true;
    }

    @Transactional
    public Boolean deleteImage(Long memberId, Long characterId, DeleteImageReqDto request) {
        Character character = characterRetriever.getCharacterByCharacterId(characterId);

        if (!character.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.CHARACTER_NOT_FOUND);
        }

        List<Image> images = request.imageIds().stream()
                .map(imageId -> imageRetriever.getImagesByImageId(imageId))
                .collect(Collectors.toList());

        imageRemover.deleteAll(images);
        return true;
    }
}

