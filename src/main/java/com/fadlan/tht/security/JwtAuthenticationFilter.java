package com.fadlan.tht.security;

import com.fadlan.tht.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/registration")
                || request.getServletPath().startsWith("/login")
                || request.getServletPath().startsWith("/banner")
                || request.getServletPath().startsWith("/swagger");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (!jwtTokenUtil.validateToken(authHeader)) {
                throw new RuntimeException("Invalid token");
            }

            String email = jwtTokenUtil.getEmailFromToken(authHeader);

            // set Authentication ke context
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    email, // principal = email
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter()
                    .write("{\"status\":108,\"message\":\"Token tidak valid atau kadaluwarsa\",\"data\":null}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
