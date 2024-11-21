package lee.io.ai.global.exception.unauthorized;


import lee.io.ai.global.exception.BusinessException;
import lee.io.ai.global.exception.ErrorCode;

public class UnAuthorizedException extends BusinessException {

    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
