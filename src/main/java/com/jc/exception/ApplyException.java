package com.jc.exception;

/**
 * 申请异常
 * Created by jasonzhu on 2017/7/13.
 */
public class ApplyException extends RuntimeException {

    public ApplyException() {
    }

    public ApplyException(String message) {
        super(message);
    }
}
