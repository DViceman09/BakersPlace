package com.sandesh.Online_Bakery.Requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
