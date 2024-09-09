package med.voll.api.controller;
import com.auth0.AuthenticationController;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.v3.oas.annotations.tags.Tag;
import med.voll.api.domain.usuarios.DatosAutenticacionUsuario;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticacion", description = "Autentica al usuario y registra en la base de datos")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository userRepository; // Repositorio de usuarios para la base de datos propia

    @Autowired
    private AutenticacionController auth0Controller; // Controlador de Auth0

    @PostMapping("/auth0")
    public ResponseEntity<String> autenticarConAuth0(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        try {
            // Autenticar con Auth0
            String token = auth0Controller.login(datosAutenticacionUsuario.login(), datosAutenticacionUsuario.clave());

            // Decodificar el token JWT para obtener información del usuario
            DecodedJWT decodedJWT = JWT.decode(token);
            String userId = decodedJWT.getSubject(); // Obtener el ID del usuario desde Auth0

            // Verificar si el usuario ya está en la base de datos, si no está, registrarlo
            Usuario usuario = userRepository.findById(userId).orElse(null);
            if (usuario == null) {
                usuario = new Usuario();
                usuario.setId(userId);
                usuario.setEmail(decodedJWT.getClaim("email").asString());
                userRepository.save(usuario); // Guardar el nuevo usuario en tu base de datos
            }

            return ResponseEntity.ok("Usuario autenticado y registrado en la base de datos.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación: " + e.getMessage());
        }
    }


}
