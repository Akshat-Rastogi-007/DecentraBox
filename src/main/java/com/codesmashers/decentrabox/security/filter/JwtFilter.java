package com.codesmashers.decentrabox.security.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codesmashers.decentrabox.security.jwt.JwtUtil;
import com.codesmashers.decentrabox.security.user.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userServiceImpl;
    private final JwtUtil jwtUtil;

    public JwtFilter(UserDetailsServiceImpl uServiceImpl, JwtUtil jwtUtil) {
        this.userServiceImpl = uServiceImpl;
        this.jwtUtil = jwtUtil;
    }

    private static final List<String> allowedPaths = List.of(
            "/app/public/register/",
            "/app/public/login");

    @SuppressWarnings("unchecked")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        String servletPath = request.getServletPath();

        if (allowedPaths.contains(servletPath)) {

            filterChain.doFilter(request, response);
            return;

        }

        if (header == null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = header.substring(7).trim();

        if (!jwtUtil.isValid(jwtToken)) {
            sendUnauthorized(response, "Invalid or expired token");
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getSubject(jwtToken);

        Claims claims = jwtUtil.getClaims(jwtToken);

        List<String> roles = (List<String>) claims.get("roles");

        if (username == null || roles == null) {
            sendUnauthorized(response, "Malformed token");
            return;
        }

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails user = userServiceImpl.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                authorities);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response,
            String message)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(
                "{\"error\": \"" + message + "\", \"status\": 401}");
    }

}
