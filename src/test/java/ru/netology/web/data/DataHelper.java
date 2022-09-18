package ru.netology.web.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    private static final String firstCardNumber = "5559 0000 0000 0001";
    private static final String secondCardNumber = "5559 0000 0000 0002";

    public static String getFirstCardNumber() {
        return firstCardNumber;
    }

    public static String getSecondCardNumber() {
        return secondCardNumber;
    }

    public static int calcTransferSum(int initTransSum, int cardBalance) {
        int transferSum = 0;
        if (cardBalance == 0) {
            throw new RuntimeException("Баланс карты = 0 и не возволяет провести тест");
        } else if (initTransSum > cardBalance || initTransSum == cardBalance) {
            transferSum = cardBalance - 1;
        } else {
            transferSum = initTransSum;
        }
        return transferSum;
    }
}