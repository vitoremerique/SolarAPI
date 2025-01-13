package com.interlig.solar.solar.infra.security;

import com.interlig.solar.solar.model.Usuario;
import com.interlig.solar.solar.repository.usuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class securityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    usuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            try {
                var cpf = tokenService.validateToken(token);
                UserDetails user = usuarioRepository.findByCpf(cpf);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.error("Usuário não encontrado para o CPF: " + cpf);
                }
            } catch (RuntimeException ex) {
                logger.error("Erro ao validar o token: " + ex.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null; // Retorna nulo se o header estiver ausente ou não começar com "Bearer "
        }
        return authHeader.substring(7).trim(); // Remove o prefixo "Bearer " e limpa espaços em branco
    }
}
