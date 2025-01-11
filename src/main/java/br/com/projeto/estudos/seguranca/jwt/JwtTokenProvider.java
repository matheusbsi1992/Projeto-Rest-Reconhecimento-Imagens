package br.com.projeto.estudos.seguranca.jwt;


import br.com.projeto.estudos.dto.TokenDTO;
import br.com.projeto.estudos.exceptions.InvalidJwtAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;


@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key: secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length: 3600000}")
    private long tempoExpiracao = 3600000;

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm;

    @PostConstruct
    protected void init() {
        secretKey = Base64.
                getEncoder().
                encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    //geracao e atualizacao de um novo token
    public TokenDTO criarToken(String userName, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tempoExpiracao);

        Long v = validity.getTime() - now.getTime();
        System.out.println("TEMPO AGORA--->>>" + now);
        System.out.println("TEMPO VALIDO--->>>" + validity);
        System.out.println("TEMPO EXISTENTE--->>>" + v);

        var acessToken = getAcessToken(userName, roles, now, validity);
        var refreshToken = getRefreshToken(userName, roles, now);
        //String userName, Boolean autenticado, Date criacao, Date expirado, String acessoToken, String atualizarToken
        return new TokenDTO(userName, Boolean.TRUE, now, validity, acessToken, refreshToken);
    }

    public TokenDTO atualizarToken(String refreshToken) {
        refreshToken = refreshToken.split("\\s+")[1].toString();
        System.out.println("NOVO TOKEN GERADO" + refreshToken);
        if (!refreshToken.isEmpty() || !refreshToken.isBlank()) {

            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken.strip());

            String userName = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            return criarToken(userName, roles);
        }
        return null;
    }

    /*Esse método gera um token JWT contendo informações sobre o usuário e seus papéis,
    assina o token usando um algoritmo de criptografia e retorna o token como uma string.
    O token é usado para autenticação e autorização em aplicações web,
    permitindo que o servidor identifique o usuário e seus privilégios em futuras requisições.*/
    private String getAcessToken(String userName, List<String> roles, Date now, Date validity) {
        //identifica a URL do servidor
        String issueUrl = ServletUriComponentsBuilder.
                fromCurrentContextPath().
                build().
                toString();

        //cria o token e retorna o valor sem espacos na frente e atras dele.
        String token = JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(userName)
                .withIssuer(issueUrl)
                .sign(algorithm)
                .strip();
        return token;
    }

    /*Esse método gera um token de atualização (refresh token) que tem uma
     validade estendida em comparação com o token de acesso padrão.
     O token contém informações sobre o usuário e seus papéis,
     é assinado usando um algoritmo de criptografia, e é retornado como uma string.
     O token de atualização é utilizado para obter novos tokens de acesso
     sem que o usuário precise se autenticar novamente.*/
    private String getRefreshToken(String userName, List<String> roles, Date now) {
        //cria o atributo de validacao para a geracao de tres horas
        Date validityRefreshToken = new Date(now.getTime() + (tempoExpiracao * 5));

        //geracao de um novo token
        String token = JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validityRefreshToken)
                .withSubject(userName)
                .sign(algorithm)
                .strip();

        return token;
    }

    /**
     * Esse método getAuthentication decodifica um token JWT,
     * carrega os detalhes do usuário associados a esse token, e
     * cria um objeto de autenticação que o Spring Security pode usar para autenticar o usuário.
     * Isso permite que a aplicação reconheça o usuário e suas permissões com base no token JWT.
     **/
    public Authentication getAuthentication(String token) {
        //recebe o objeto decodificado do token
        DecodedJWT decodedJWT = decodificarToken(token);
        //identificacao do usuario
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        //detalhes do usuario, senha nao identificada ja que passa o token e as permissoes
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //decodificar o token criptografado e verificar o token gerado
    private DecodedJWT decodificarToken(String token) {

        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier jwtVerifier = JWT.require(alg).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return decodedJWT;
    }

    //obtem o token totalmente gerado posteriormente, mas atraves do cabecalho
    public String resolveToken(HttpServletRequest httpServletRequest) {
        var authHeader = httpServletRequest.getHeader("Authorization");//.split("\\s+");

        if (authHeader == null) {
            return null;
        }
        System.out.println("AQUI-->>" + authHeader);
        return authHeader.split("\\s+")[1].toString();
    }

    //validar o token
    public boolean validarToken(String token) {
        DecodedJWT decodedJWT = decodificarToken(token);
        try {
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception ex) {
            throw new InvalidJwtAuthenticationException("E necessario Validar!!!" + ex.getMessage());
        }
    }

}