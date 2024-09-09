package med.voll.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutenticacionUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticacion", description = "Autentica al usuario sin generar un token")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<String> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);

        // Retornar una respuesta indicando éxito sin generar un token
        if (usuarioAutenticado.isAuthenticated()) {
            return ResponseEntity.ok("Usuario autenticado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Autenticación fallida.");
        }
    }
}