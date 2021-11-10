package com.instogramm.instogramm.payload;

import lombok.Getter;

@Getter
public class InvalidLoginResponce {
    private String username;
    private String password;

    public InvalidLoginResponce() {
        this.username = "Invalid username";
        this.password = "Invalid password";
    }
}
