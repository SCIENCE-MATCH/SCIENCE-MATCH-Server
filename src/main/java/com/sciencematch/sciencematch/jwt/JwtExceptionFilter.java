package com.sciencematch.sciencematch.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException exception) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, exception);
        } catch (NullPointerException e) {
            response.setStatus(400);
            response.setContentType("application/json; charset=UTF-8");

            response.getWriter().write(objectMapper.writeValueAsString(ApiResponseDto.error(ErrorStatus.VALIDATION_EXCEPTION)));
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse res, Throwable ex) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        res.getWriter().write(objectMapper.writeValueAsString(ApiResponseDto.error(ErrorStatus.INVALID_ACCESS_TOKEN_EXCEPTION, ex.getMessage())));
    }
}
