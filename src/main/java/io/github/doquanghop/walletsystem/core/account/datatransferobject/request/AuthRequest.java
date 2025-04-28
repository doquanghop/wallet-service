package io.github.doquanghop.walletsystem.core.account.datatransferobject.request;


import lombok.Getter;

@Getter
public class AuthRequest {
    private final String phoneNumber;
    private final String password;

    public AuthRequest(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
