package com.potato112.springservice.domain.common.email;

public interface PasswordService {

    String generateRandomPassword();
    String generateHashedPassword(String password);
}
