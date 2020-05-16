package com.potato112.springservice.jms.doclock;

public class AlreadyLockedException extends RuntimeException {

    private String lockingUserLogin;

    public AlreadyLockedException(String message, String lockingUserLogin) {
        super(message);
        this.lockingUserLogin = lockingUserLogin;
    }

    public String getLockingUserLogin() {
        return lockingUserLogin;
    }
}
