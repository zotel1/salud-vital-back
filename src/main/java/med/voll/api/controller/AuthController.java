package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DatosRegistroUsuario;
import med.voll.api.domain.usuarios.DatosAutenticacionUsuario;
import med.voll.api.service.Auth0Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private Auth0Service auth0Service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario) {
        try {
            auth0Service.registerUser(datosRegistroUsuario.getEmail(), datosRegistroUsuario.getPassword());
            return ResponseEntity.ok("Usuario registrado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        // Aquí iría la lógica de autenticación con Auth0
        return ResponseEntity.ok("Usuario autenticado.");
    }
}