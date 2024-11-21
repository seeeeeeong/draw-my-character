package lee.io.ai.domain.member.enums;

public enum Provider {

    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    APPLE("APPLE"),
    ;

    private final String key;

    Provider(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
