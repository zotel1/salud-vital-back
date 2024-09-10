package med.voll.api.service;

import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class Auth0Service {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${tu-dominio-auth0-com}")
    private String domain;

    @Value("${tu-client-id}")
    private String clientId;

    @Value("${tu-client-secret}")
    private String clientSecret;

    @Value("${tu-dominio-auth0-com}")
    private String apiBaseUrl;

    public void registerUser(String email, String password) throws Exception {
        // Registrar el usuario en Auth0
        String auth0UserId = registerUserInAuth0(email, password);

        // Guardar el usuario en la base de datos local
        Usuario usuario = new Usuario();
        usuario.setLogin(email);
        usuario.setClave(password); // Asegúrate de hashear la contraseña
        usuarioRepository.save(usuario);
    }

    private String registerUserInAuth0(String email, String password) throws Exception {
        // Crear el usuario en Auth0 usando la API de gestión
        String createUserUrl = apiBaseUrl + "api/v2/users";
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);
        payload.put("connection", "Username-Password-Authentication");

        // Lógica para obtener el token de acceso a Auth0 Management API
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getManagementApiToken());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(createUserUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            JSONObject responseBody = new JSONObject(response.getBody());
            return responseBody.getString("user_id");
        } else {
            throw new Exception("Error al registrar usuario en Auth0");
        }
    }

    private String getManagementApiToken() throws Exception {
        // Obtener el token de Auth0 Management API
        String tokenUrl = apiBaseUrl + "oauth/token";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("audience", apiBaseUrl + "api/v2/");
        body.put("grant_type", "client_credentials");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject responseBody = new JSONObject(response.getBody());
            return responseBody.getString("access_token");
        } else {
            throw new Exception("Error al obtener el token de Auth0");
        }
    }

    public String loginUser(String email, String password) throws Exception {
        String tokenUrl = apiBaseUrl + "oauth/token";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("grant_type", "password");
        body.put("username", email);
        body.put("password", password);
        body.put("scope", "openid");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject responseBody = new JSONObject(response.getBody());
            return responseBody.getString("access_token");
        } else {
            throw new Exception("Error al autenticar el usuario: " + response.getBody());
        }
    }

}
