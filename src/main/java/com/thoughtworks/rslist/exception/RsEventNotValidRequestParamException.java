package com.thoughtworks.rslist.exception;

/**
 * @author Boyu Yuan
 * @date 2020/9/16 22:36
 */
public class RsEventNotValidRequestParamException extends RuntimeException {
    private  String errorMessage;

    public RsEventNotValidRequestParamException(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    @Override
    public String getMessage() {
        return errorMessage;
    }
}
