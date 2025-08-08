package org.scoula.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.scoula.security.util.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;

@Component
public class AuthenticationErrorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(req, res); // ✅ 이거!
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            JsonResponse.sendError(res, HttpStatus.UNAUTHORIZED, "토큰의 유효시간이 지났습니다.");
        } catch (io.jsonwebtoken.UnsupportedJwtException | io.jsonwebtoken.MalformedJwtException e) {
            JsonResponse.sendError(res, HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (io.jsonwebtoken.security.SignatureException e) {
            JsonResponse.sendError(res, HttpStatus.UNAUTHORIZED, "토큰 서명 검증 실패");
        } catch (Exception e) {
            // ✅ 남는 예외도 500 JSON으로 마무리 (톰캣 HTML 금지)
            JsonResponse.sendError(res, HttpStatus.INTERNAL_SERVER_ERROR, "인증 처리 중 오류");
        }
    }
}
