package io.github.doquanghop.walletsystem.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.doquanghop.walletsystem.core.account.service.AccountService;
import io.github.doquanghop.walletsystem.infrastructure.constant.SecurityConstants;
import io.github.doquanghop.walletsystem.infrastructure.security.UserDetail;
import io.github.doquanghop.walletsystem.infrastructure.utils.TokenExtractor;
import io.github.doquanghop.walletsystem.shared.exceptions.AppException;
import io.github.doquanghop.walletsystem.shared.types.ApiResponse;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter implements Ordered {
    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final int ORDER = 100;

    @Override
    public int getOrder() {
        return ORDER;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean shouldSkip = SecurityConstants.PUBLIC_ENDPOINTS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

        if (shouldSkip) {
            log.debug("Skipping JWT authentication for request [{} {}]",
                    request.getMethod(), path);
        }

        return shouldSkip;
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("Authenticating request [{} {}] | IP={} | UA={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
        );

        String token = TokenExtractor.extractToken(request.getHeader("Authorization"));

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetail userDetail = accountService.authenticate(token);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetail, null, userDetail.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

                log.info("Authentication successful for userId={}", userDetail.getId());

                filterChain.doFilter(request, response);
                return;
            } catch (AppException e) {
                log.info("Authentication failed | Code={} | Type={}",
                        e.getExceptionCode().getCode(), e.getExceptionCode().getType());

                ApiResponse<Object> apiResponse = buildErrorResponse(e);
                sendErrorResponse(response, apiResponse);

                return;

            } catch (Exception e) {
                log.info("Authentication failed: Unexpected error | Message={}", e.getMessage(), e);

                ApiResponse<Object> apiResponse = buildUnexpectedErrorResponse();
                sendErrorResponse(response, apiResponse);

                return;
            }
        }
        log.info("No token provided in request [{} {}]", request.getMethod(), request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    private ApiResponse<Object> buildErrorResponse(AppException e) {
        Map<String, Object> errors = new HashMap<>();
        String type = e.getExceptionCode().getType();
        errors.put("type", type);

        return ApiResponse.build()
                .withCode(e.getExceptionCode().getCode())
                .withMessage(e.getMessage())
                .withErrors(errors);
    }

    private ApiResponse<Object> buildUnexpectedErrorResponse() {
        Map<String, Object> errors = new HashMap<>();
        errors.put("type", "UNEXPECTED_ERROR");

        return ApiResponse.build()
                .withCode(5000)
                .withMessage("UNEXPECTED_ERROR")
                .withErrors(errors);
    }

    private void sendErrorResponse(HttpServletResponse response, ApiResponse<Object> apiResponse) throws IOException {
        response.setStatus(apiResponse.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}