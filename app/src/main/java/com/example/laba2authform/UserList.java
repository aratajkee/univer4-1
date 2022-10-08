package com.example.laba2authform;

import java.util.ArrayList;

public class UserList {
    public static ArrayList<String> userList;
    public UserList(){
        userList = new ArrayList<String>();
    }
    public void addUser(String username){

        userList.add(username);
    }
}
