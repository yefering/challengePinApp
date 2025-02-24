package com.qaautomation.utils;

import com.qaautomation.models.User;

public class TestDataBuilder {

    public static User createDefaultUser() {
        return new User(
                0,                 // id
                "UserPepe",             // username
                "Pepe",           // firstName
                "Gomez",             // lastName
                "UserPepe@gmail.com",   // email
                "123asd",          // password
                "1237891",          // phone
                0                  // userStatus
        );
    }

    public static User createDefaultUser(String username) {
        return new User(
                0,                 // id
                username,             // username
                "Pepe",           // firstName
                "Gomez",             // lastName
                "UserPepe@gmail.com",   // email
                "123asd",          // password
                "1237891",          // phone
                0                  // userStatus
        );
    }
}