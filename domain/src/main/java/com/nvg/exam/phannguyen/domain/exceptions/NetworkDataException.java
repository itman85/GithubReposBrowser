package com.nvg.exam.phannguyen.domain.exceptions;

/**
 * Created by phannguyen on 7/29/17.
 */

public class NetworkDataException extends Exception {
    String errMessage;

    public NetworkDataException(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public String getMessage() {
        return errMessage;
    }
}
