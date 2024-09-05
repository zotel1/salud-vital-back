package med.voll.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SaludVitalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaludVitalAppApplication.class, args);
	}


}
@Configuration
public static Myconfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer () {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATH");
			}
		};
	}
}
