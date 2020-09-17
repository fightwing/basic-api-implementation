package com.thoughtworks.rslist.exception;

/**
 * @author Boyu Yuan
 * @date 2020/9/16 21:46
 */
public class RsEventNotValidException extends RuntimeException{
    private  String errorMessage;

    public RsEventNotValidException(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    @Override
    public String getMessage() {
        return errorMessage;
    }
}
