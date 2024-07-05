package com.darkin.electronicordersystem.utility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HashPasswordTest {
    private String salt;

    @Test
    @DisplayName("Testing Salt Generation")
    void generateSalt() {
        salt = HashPassword.generateSalt();
        assertNotEquals(null, salt);
//        System.out.println(salt);
    }

    @Test
    @DisplayName("Compare Password")
    void checkPasswordHash(){
        String password = "Walker00";
        this.salt = HashPassword.generateSalt();
        System.out.println("Salt: " + salt);

        Optional<String> secretPassword1 = HashPassword.hashPassword(password, this.salt);
        Optional<String> secretPassword2 = HashPassword.hashPassword(password, this.salt);

        if(secretPassword2.isPresent() && secretPassword1.isPresent()) {
            assertEquals(secretPassword1.get(), secretPassword2.get());
            System.out.println("Secret Password: " + secretPassword1.get());
        }
    }
}