package io.github.doquanghop.walletsystem.core.account.datatransferobject;

import java.util.Date;
import java.util.List;

public record TokenMetadataDTO(
        String userId,
        List<String> roles,
        String sessionId,
        Date issuedAt
) {
}
