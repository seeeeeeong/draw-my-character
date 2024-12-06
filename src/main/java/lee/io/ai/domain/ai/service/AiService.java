package lee.io.ai.domain.ai.service;

import lee.io.ai.domain.ai.dto.*;
import lee.io.ai.domain.character.entity.Character;
import lee.io.ai.domain.character.service.CharacterRetriever;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.service.MemberRetriever;
import lee.io.ai.domain.member.service.MemberService;
import lee.io.ai.global.exception.BusinessException;
import lee.io.ai.global.exception.ErrorCode;
import lee.io.ai.global.exception.notfound.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiService {

    private final OpenAiChatModel openAiChatModel;
    private final OpenAiImageModel openAiImageModel;

    private final CharacterRetriever characterRetriever;
    private final MemberRetriever memberRetriever;

    @Value("${image.features.prompt}")
    private String featuresPrompt;

    @Value("${image.create.prompt}")
    private String createPrompt;

    @Value("${image.character.prompt}")
    private String characterPrompt;

    @Value("${spring.task.execution.pool.core-size}")
    private Integer poolCoreSize;

    public AnalyzeImageResDto analyzeImage(AnalyzeImageReqDto request) {

        URL imageUrl = getUrl(request.imageUrl());
        String prompt = featuresPrompt;
        UserMessage userMessage = new UserMessage(prompt, Collections.singletonList(new Media(MediaType.IMAGE_JPEG, imageUrl)));

        String features = openAiChatModel.call(userMessage);
//        String featuresAfterColon = features.split(":", 2)[1].trim();
        return AnalyzeImageResDto.of(request.imageUrl(), features);
    }

    public GenerateImageResDto generateImage(GenerateImageReqDto request) {
        ExecutorService executorService = Executors.newFixedThreadPool(poolCoreSize);
        String prompt = createPrompt + request.features();

        OpenAiImageOptions options = OpenAiImageOptions.builder()
                .withQuality("hd")
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .build();

        List<CompletableFuture<String>> futureImageUrls = new ArrayList<>();

        for (int i=0; i < request.numberOfImages(); i++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                ImagePrompt imagePrompt = new ImagePrompt(prompt, options);
                return openAiImageModel.call(imagePrompt).getResult().getOutput().getUrl();
            });
            futureImageUrls.add(future);
        }

        List<String> imageUrls = futureImageUrls.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        executorService.shutdown();

        return GenerateImageResDto.of(imageUrls, request.features());
    }


    public GenerateImageWithActionResDto generateImageWithAction(Long memberId, GenerateImageWithActionReqDto request) {
        ExecutorService executorService = Executors.newFixedThreadPool(poolCoreSize);

        Character character = characterRetriever.getCharacterByCharacterId(request.characterId());

        if (!character.getMember().getId().equals(memberId)) {
            throw new EntityNotFoundException(ErrorCode.CHARACTER_NOT_FOUND);
        }

        PromptTemplate promptTemplate = new PromptTemplate(characterPrompt);
        String prompt = promptTemplate.create(Map.of("characterFeatures", character.getFeatures(), "action", request.action())).toString();

        OpenAiImageOptions options = OpenAiImageOptions.builder()
                .withQuality("hd")
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .build();

        List<CompletableFuture<String>> futureImageUrls = new ArrayList<>();

        for (int i=0; i < request.numberOfImages(); i++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                ImagePrompt imagePrompt = new ImagePrompt(prompt, options);
                return openAiImageModel.call(imagePrompt).getResult().getOutput().getUrl();
            });
            futureImageUrls.add(future);
        }

        List<String> imageUrls = futureImageUrls.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        executorService.shutdown();

        return GenerateImageWithActionResDto.of(imageUrls, request.action());
    }


    private URL getUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCode.MALFORMED_URL);
        }
    }

}
