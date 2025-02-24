package com.qaautomation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                       // Genera automáticamente getters, setters, equals, hashCode y toString
@NoArgsConstructor          // Genera un constructor sin argumentos
@AllArgsConstructor         // Genera un constructor con todos los argumentos
public class User {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;
}
