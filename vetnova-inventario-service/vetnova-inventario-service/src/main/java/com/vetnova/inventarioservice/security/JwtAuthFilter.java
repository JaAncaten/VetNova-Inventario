package com.vetnova.inventarioservice.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token no enviado o formato incorrecto");
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.validarToken(token);

            String correo = claims.getSubject();
            String rol = claims.get("rol", String.class);

            request.setAttribute("correo", correo);
            request.setAttribute("rol", rol);

            if (!tienePermisoInventario(rol)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Acceso denegado. Solo ADMIN o BODEGA pueden acceder a inventario");
                return;
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token invalido o expirado");
        }
    }

    private boolean tienePermisoInventario(String rol) {
        return "ADMIN".equals(rol) || "BODEGA".equals(rol);
    }
}