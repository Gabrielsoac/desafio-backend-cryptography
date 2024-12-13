package com.gabryellow.cryptography.config;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class APIRestErrorBuilder {

    private Instant timestamp;
    private int code;
    private String name;
    private String error;

    private APIRestErrorBuilder(){

    }

    public static APIRestErrorBuilder aAPIRestError(){

        APIRestErrorBuilder errorBuilder = new APIRestErrorBuilder();
        return setDefaultData(errorBuilder);
    }

    public static APIRestErrorBuilder setDefaultData(APIRestErrorBuilder errorBuilder){

        errorBuilder.code = 404;
        errorBuilder.name = HttpStatus.NOT_FOUND.name();
        errorBuilder.timestamp = Instant.now();
        errorBuilder.error = "Not Found";

        return errorBuilder;
    }

    public APIRestErrorBuilder withCode(int code){
        this.code = code;
        return this;
    }

    public APIRestErrorBuilder withName(String name){
        this.name = name;
        return this;
    }

    public APIRestErrorBuilder withError(String error){
        this.error = error;
        return this;
    }

    public APIRestErrorBuilder withTimestamp(Instant timestamp){
        this.timestamp = timestamp;
        return this;
    }

    public APIRestError build(){
        return new APIRestError(this.timestamp, this.code, this.name, this.error);
    }
}
