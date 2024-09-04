//package med.voll.api.infra.security;


//import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfigs {

//    @Value("${auth0.domain}")
//    private String domain;

//    @Value("${auth0.clientId}")
//    private String clientId;

 //   @Value("${auth0.clientSecret}")
//    private String clientSecret;

 //   @Value("${auth0.audience}")
 //   private String audience;

 //   @Bean
  //  public ClientRegistrationRepository clientRegistrationRepository() {
  //      return new InMemoryClientRegistrationRepository(this.auth0ClientRegistration());
  //  }

 //   private ClientRegistration auth0ClientRegistration() {
  //      return ClientRegistration.withRegistrationId("auth0")
   //             .clientId(clientId)
    //            .clientSecret(clientSecret)
    //            .scope("openid", "profile", "email")
        //        .authorizationUri("https://" + domain + "/authorize")
      //          .tokenUri("https://" + domain + "/oauth/token")
          //      .userInfoUri("https://" + domain + "/userinfo")
     //           .userNameAttributeName("sub")
      //          .clientName("Auth0")
          //      .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
        //        .build();
  //  }

 //   @Bean
 //   public OAuth2AuthorizedClientService authorizedClientService() {
   ///     return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
  //  }
//}