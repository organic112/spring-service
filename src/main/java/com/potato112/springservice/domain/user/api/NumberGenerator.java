package com.potato112.springservice.domain.user.api;

public class NumberGenerator {


    public static final String NUMBERS_RANGE_STRING = "9876543210";

    private NumberGenerator() {
    }

    public static String generateRandomNumbers(int numberOfChars) {
        char[] chars = NUMBERS_RANGE_STRING.toCharArray();
        return generate(chars, numberOfChars);
    }

    public static String generate(char[] chars, int length) {
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            double randomValue = Math.random();
            int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
            char exposedCharacter = chars[randomIndex];
            passwordBuilder.append(exposedCharacter);
        }
        return passwordBuilder.toString();
    }


}
