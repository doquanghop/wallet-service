package io.github.doquanghop.walletsystem.core.account.datatransferobject.request;

import io.github.doquanghop.walletsystem.shared.annotation.logging.MaskSensitive;
import lombok.Data;

@Data
public class LogoutRequest {
    private @MaskSensitive String accessToken;
}
