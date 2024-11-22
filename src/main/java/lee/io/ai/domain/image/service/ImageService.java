package lee.io.ai.domain.image.service;

import lee.io.ai.domain.image.dto.CreateImageReqDto;
import lee.io.ai.domain.image.dto.CreateImageResDto;
import lee.io.ai.domain.image.dto.GetImageFeaturesReqDto;
import lee.io.ai.domain.image.dto.GetImageFeaturesResDto;
import lee.io.ai.global.exception.BusinessException;
import lee.io.ai.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final OpenAiChatModel openAiChatModel;
    private final OpenAiImageModel openAiImageModel;

    @Value("${image.features.prompt}")
    private String featuresPrompt;

    @Value("${image.create.prompt}")
    private String createPrompt;

    @Value("${spring.task.execution.pool.core-size}")
    private Integer poolCoreSize;


    public GetImageFeaturesResDto getImageFeatures(GetImageFeaturesReqDto request) {

        String prompt = featuresPrompt;
        UserMessage userMessage = new UserMessage(prompt, Collections.singletonList(new Media(MediaType.IMAGE_JPEG, request.imageUrl())));

        String features = openAiChatModel.call(userMessage);

        return new GetImageFeaturesResDto(request.imageUrl(), features);
    }

    public CreateImageResDto createImagesByFeatures(CreateImageReqDto request) {
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

        return new CreateImageResDto(imageUrls, request.features());
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

