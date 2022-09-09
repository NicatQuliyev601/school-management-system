package com.nicat.schoolmanagementsystem.exception;

public class ServerErrorException extends RuntimeException {

    private static final long serialVersionUID = 1416594412164612L;

    public ServerErrorException() {
        super("Request can not be processed at the moment, please try again later.");
    }

    public ServerErrorException(Throwable cause) {
        super(cause);
    }

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
