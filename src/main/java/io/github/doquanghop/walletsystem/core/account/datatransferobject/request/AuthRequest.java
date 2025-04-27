package io.github.doquanghop.walletsystem.core.account.datatransferobject.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AuthRequest {
    private String phoneNumber;
    private String password;
}
