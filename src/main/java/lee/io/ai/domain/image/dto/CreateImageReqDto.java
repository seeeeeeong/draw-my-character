package lee.io.ai.domain.image.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class CreateImageReqDto {

    private String features;

    @NotNull
    @Range(min = 1, max = 5)
    private int numberOfImages;

}
