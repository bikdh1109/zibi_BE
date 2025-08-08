package org.scoula.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.scoula.security.util.JwtProcessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtProcessor jwtProcessor;
    private final UserDetailsService userDetailsService;

    private Authentication getAuthentication(String token) {
        if (token == null || token.isBlank()) return null;
        String username = jwtProcessor.getUsername(token);
        if (username == null || username.isBlank()) return null;

        try {
            UserDetails ud = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException | IllegalArgumentException ex) {
            return null; // ✅ 인증 실패로만 처리
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/v1/kakao/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String bearer = req.getHeader(AUTHORIZATION_HEADER);
        if (bearer != null && bearer.startsWith(BEARER_PREFIX)) {
            String token = bearer.substring(BEARER_PREFIX.length());
            try {
                if (jwtProcessor.validateToken(token)) {
                    Authentication auth = getAuthentication(token);
                    if (auth != null) SecurityContextHolder.getContext().setAuthentication(auth);
                    else SecurityContextHolder.clearContext();
                }
            } catch (Exception e) {
                // ✅ 여기서도 절대 throw 금지
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(req, res);
    }
}


