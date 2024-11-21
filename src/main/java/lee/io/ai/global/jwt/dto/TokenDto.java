package lee.io.ai.global.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {

    private Long memberId;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String expiresIn;

}
