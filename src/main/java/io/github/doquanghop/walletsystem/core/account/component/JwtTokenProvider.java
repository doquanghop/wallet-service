package io.github.doquanghop.walletsystem.core.account.component;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.TokenDTO;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.TokenMetadataDTO;
import io.github.doquanghop.walletsystem.core.account.exception.TokenException;
import io.github.doquanghop.walletsystem.shared.exceptions.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    @Value("${spring.security.jwt.secret}")
    private String secretKey;

    @Value("${spring.security.jwt.expiration}")
    private long expiration;

    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_SESSION_ID = "sessionId";
    private static final String ISSUER = "auth-service";

    public TokenDTO generateTokens(TokenMetadataDTO tokenMetadata) {
        Date issuedAt = tokenMetadata.issuedAt();
        Date accessValidity = new Date(issuedAt.getTime() + expiration * 1000);
        Date refreshValidity = new Date(issuedAt.getTime() + expiration * 1500);


        String accessToken = generateToken(tokenMetadata.userId(), tokenMetadata.roles(), tokenMetadata.sessionId(), issuedAt, accessValidity);
        String refreshToken = generateToken(tokenMetadata.userId(), null, null, issuedAt, refreshValidity);

        return new TokenDTO(tokenMetadata.userId(), accessToken, accessValidity, refreshToken, refreshValidity);
    }

    private String generateToken(String userId, List<String> roles, String sessionId, Date issuedAt, Date expiration) {
        try {
            var claimsSetBuilder = new JWTClaimsSet.Builder()
                    .subject(userId)
                    .issuer(ISSUER)
                    .issueTime(issuedAt)
                    .expirationTime(expiration)
                    .jwtID(UUID.randomUUID().toString())
                    .claim(CLAIM_SESSION_ID, sessionId);
            if (roles != null) {
                claimsSetBuilder.claim(CLAIM_ROLE, roles);
            }

            JWSObject jwsObject = new JWSObject(
                    new JWSHeader(JWSAlgorithm.HS256),
                    new Payload(claimsSetBuilder.build().toJSONObject())
            );

            jwsObject.sign(new MACSigner(secretKey));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(TokenException.UNEXPECTED_TOKEN_ERROR);
        }
    }

    public JWTClaimsSet verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSObject jwsObject = JWSObject.parse(token);

            if (!jwsObject.verify(new MACVerifier(secretKey))) {
                throw new AppException(TokenException.INVALID_TOKEN);
            }

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date expirationTime = claims.getExpirationTime();

            if (expirationTime != null && expirationTime.before(new Date())) {
                throw new AppException(TokenException.EXPIRED_TOKEN);
            }

            return claims;
        } catch (ParseException | JOSEException e) {
            throw new AppException(TokenException.UNEXPECTED_TOKEN_ERROR);
        }
    }

    public boolean validateToken(String token) {
        return verifyToken(token) != null;
    }

    public Date getExpirationFromToken(String token) {
        return verifyToken(token)
                .getExpirationTime();
    }

    public String getSessionIdFromClaims(JWTClaimsSet claims) {
        Object sessionId = claims.getClaim(CLAIM_SESSION_ID);
        if (sessionId == null) {
            throw new AppException(TokenException.INVALID_TOKEN);
        }
        return sessionId.toString();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromClaims(JWTClaimsSet claims) {
        return (List<String>) claims.getClaim(CLAIM_ROLE);
    }

    public JWTClaimsSet verifyRefreshToken(String refreshToken, String accessToken) {
        JWTClaimsSet refreshClaims = verifyToken(refreshToken);
        JWTClaimsSet accessClaim = verifyToken(accessToken);
        if (!getSessionIdFromClaims(refreshClaims).equals(getSessionIdFromClaims(accessClaim))) {
            throw new AppException(TokenException.INVALID_TOKEN);
        }
        return accessClaim;
    }

    public TokenDTO refreshToken(String refreshToken, String accessToken) {
        JWTClaimsSet refreshClaims = verifyRefreshToken(refreshToken, accessToken);

        TokenMetadataDTO newMetadata = new TokenMetadataDTO(
                refreshClaims.getSubject(),
                getRolesFromClaims(refreshClaims),
                getSessionIdFromClaims(refreshClaims),
                new Date()
        );

        return generateTokens(newMetadata);
    }
}
