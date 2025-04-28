package io.github.doquanghop.walletsystem.core.account.datatransferobject.request;

public class LoginRequest extends AuthRequest {
    public LoginRequest(String phoneNumber, String password) {
        super(phoneNumber, password);
    }
}
