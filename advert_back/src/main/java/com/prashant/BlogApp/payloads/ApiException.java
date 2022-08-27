package com.prashant.BlogApp.payloads;

public class ApiException extends RuntimeException {
    
    public ApiException() {
        super();
    }    

    public ApiException(String message) {
        super(message);
    }
}
