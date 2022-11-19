package com.example.laba2authform;

import java.util.ArrayList;

public class User {
    private String username,password;
    public ArrayList<String> userList;
    public User(){
        this.username = null;
        this.password = null;


    }
    public User (String username, String password){ //ХУЙХУЙ ХУЙ ХУЙ ХУЙ ХУЙ
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
