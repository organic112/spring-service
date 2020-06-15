package com.potato112.springservice.domain.user.api;

import com.potato112.springservice.domain.common.email.PasswordService;
import org.jboss.seam.security.management.PasswordHash;

import java.security.GeneralSecurityException;

public class PasswordSimpleImpl implements PasswordService {


    private static final String VALID_CHARS_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int RANDOM_PASS_HASH_ITER = 100;
    private static final int RANDOM_PASS_LENGTH = 10;


    @Override
    public String generateRandomPassword() {

        char[] chars = VALID_CHARS_STRING.toCharArray();
        return NumberGenerator.generate(chars, RANDOM_PASS_LENGTH);
    }

    @Override
    public String generateHashedPassword(String password) {

        byte[] toByte = {};
        try {
            char[] passToCharTable = password.toCharArray();
            PasswordHash passwordHash = new PasswordHash();
            return passwordHash.createPasswordKey(passToCharTable, toByte, RANDOM_PASS_HASH_ITER);
        } catch (GeneralSecurityException e) {
            return null;
        }
    }
}
