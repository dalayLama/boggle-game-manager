package com.playhub.game.boggle.manager.exceptions;

import com.playhub.common.errors.exceptions.PlayhubException;

public abstract class BoggleGameManagerException extends PlayhubException {

    private final Error error;

    public BoggleGameManagerException(Error error) {
        this.error = error;
    }

    public BoggleGameManagerException(String message, Error error) {
        super(message);
        this.error = error;
    }

    public BoggleGameManagerException(String message, Throwable cause, Error error) {
        super(message, cause);
        this.error = error;
    }

    public BoggleGameManagerException(Throwable cause, Error error) {
        super(cause);
        this.error = error;
    }

    public BoggleGameManagerException(String message,
                                      Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace,
                                      Error error) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.error = error;
    }

    @Override
    public String getMessageSourceResolvableTitle() {
        return error.getTitleCode();
    }

    @Override
    public String getMessageSourceResolvableDetailMessage() {
        return error.getMessageCode();
    }

    @Override
    public String getErrorCode() {
        return error.getCode();
    }
}
