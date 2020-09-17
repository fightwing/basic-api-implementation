package com.thoughtworks.rslist.exception;

import lombok.Data;

/**
 * @author Boyu Yuan
 * @date 2020/9/16 21:54
 */

public class Error {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
