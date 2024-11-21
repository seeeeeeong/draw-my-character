package lee.io.ai.global.exception.badrequest;


import lee.io.ai.global.exception.BusinessException;
import lee.io.ai.global.exception.ErrorCode;

public class InvalidParameterException extends BusinessException {

    public InvalidParameterException(ErrorCode errorCode) {
        super(errorCode);
    }
}
