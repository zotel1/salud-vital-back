package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    @Value("${auth0.clientSecret}")
    private String clientSecret;

    public String generarToken(OidcUser principal) {
        String token = JWT.create()
                .withSubject(principal.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .sign(Algorithm.HMAC256(clientSecret));
        System.out.println("Generated Token: " + token);
        return token;
    }


    public boolean validarToken(String token) {
        try {
            // Verifica que el token tenga el formato correcto y sea válido
            JWT.require(Algorithm.HMAC256(clientSecret)).build().verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            // Maneja la excepción si el token no es válido
            return false;
        }
    }

    public String getUsuario(String token) {
        // Obtiene el sujeto (usuario) del token si es válido
        return JWT.require(Algorithm.HMAC256(clientSecret)).build().verify(token).getSubject();
    }
}
