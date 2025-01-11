package br.com.projeto.estudos.util;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.*;

@Component
public class Util {


    public static String gerarSenha(int comprimento) {
        SecureRandom random = new SecureRandom();

        String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder senha = new StringBuilder(comprimento);

        for (int i = 0; i < comprimento; i++) {
            senha.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
        }
        return senha.toString();
    }

    //@Bean
    public static String passwordEncryptEncoder(String password) {
        Map<String, PasswordEncoder> enconders = new HashMap<>();

        Pbkdf2PasswordEncoder secretKeyFactoryAlgorithm = new
                Pbkdf2PasswordEncoder("", 95, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        enconders.put("pbkdf2", secretKeyFactoryAlgorithm);

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", enconders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(secretKeyFactoryAlgorithm);

        return passwordEncoder.encode(password).split("\\{pbkdf2}")[1];
    }


    public static <T> Page<T> listarParaPaginar(int pagina, int itens, List<T> lista) {
        List<T> listaObtida = new ArrayList<>();
        Pageable pageRequest = PageRequest.of(pagina, itens);
        int inicio  = (int) pageRequest.getOffset();
        int fim     = Math.min((inicio + pageRequest.getPageSize()), lista.size());
        listaObtida = lista.subList(inicio, fim);
        return new PageImpl<>(listaObtida, pageRequest, lista.size());
    }


}
