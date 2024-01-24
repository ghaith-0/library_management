package com.example.librarymanagementsystem.Configs.APIResponseConfigs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIResponse<T> {

    private int statusCode;
    private boolean success;
    private T result;
    private String message;

    public APIResponse(int statusCode, boolean success, T result, String message) {
        this.statusCode = statusCode;
        this.success = success;
        this.result = result;
        this.message = message;
    }
}
