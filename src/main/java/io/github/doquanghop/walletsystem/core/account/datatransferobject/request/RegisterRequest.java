package io.github.doquanghop.walletsystem.core.account.datatransferobject.request;

import lombok.Getter;

@Getter
public class RegisterRequest extends AuthRequest {
    private final String fullName;
    private final String email;

    public RegisterRequest(String phoneNumber, String password, String fullName, String email) {
        super(phoneNumber, password);
        this.fullName = fullName;
        this.email = email;
    }
}
