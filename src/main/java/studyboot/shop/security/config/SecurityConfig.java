package studyboot.shop.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.method.HandlerMethod;

@Configuration
@EnableWebSecurity // bezpieczeństwo web, czyli wchodzące zapytania
@EnableMethodSecurity // zabezpiecza poszczególne metody, różne stopnie dostępu
public class SecurityConfig {

    // obiekt do szyfrowania hasła
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    //filtr sprawdza każde zapytanie i filtruje je wg. poziomu bezpieczeństwa
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //wyłączam tą ochronę
                .sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .httpBasic(Customizer.withDefaults()) //rodzaj autoryzacji
                .authorizeHttpRequests(auth -> auth //weryfikacja dostępów
                                .requestMatchers(HttpMethod.GET,"/products").permitAll() //jakie zapytania mają być zabezpieczone i w jaki sposób
                                .requestMatchers(HttpMethod.GET, "/products/{id}").authenticated()
//                                  .requestMatchers(HttpMethod.GET, "products/{id}").hasAnyRole("USER", "ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")

                );
       return http.build();
    }
}
