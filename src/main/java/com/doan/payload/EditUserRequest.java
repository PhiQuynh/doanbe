package com.doan.payload;

import lombok.Data;

@Data
public class EditUserRequest {
    private String roleName;
    private String username;
    private String password;
    private String newPassword;
}
