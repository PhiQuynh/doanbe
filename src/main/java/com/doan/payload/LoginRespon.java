package com.doan.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
public class LoginRespon {

    private String accessToken;
    private String tokenType;
    private Map<String, String> errors = new HashMap<>();

    public LoginRespon(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }

    public LoginRespon(Map<String, String> errors) {
        this.errors = errors;
    }

}
