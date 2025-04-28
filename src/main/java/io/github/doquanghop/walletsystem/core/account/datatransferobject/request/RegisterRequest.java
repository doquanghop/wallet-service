package io.github.doquanghop.walletsystem.core.account.datatransferobject.request;

import lombok.Getter;

@Getter
public class RegisterRequest extends AuthRequest {
    private String fullName;
    private String email;
}
