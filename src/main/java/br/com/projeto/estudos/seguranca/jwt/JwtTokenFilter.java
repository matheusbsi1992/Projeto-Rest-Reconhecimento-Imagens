package br.com.projeto.estudos.seguranca.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {


    private JwtTokenProvider jwtTokenProvider;


   /* public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }*/
   @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        Objects.requireNonNull(jwtTokenProvider);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validarToken(token)) {
            //obtem o usuario autenticado
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            if (authentication != null) {
                //obtem o usuario autenticado em futuras requisicoes
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }


}
