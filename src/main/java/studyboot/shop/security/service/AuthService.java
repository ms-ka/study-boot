package studyboot.shop.security.service;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import studyboot.shop.model.entity.User;
import studyboot.shop.security.dto.LoginRequestDTO;
import studyboot.shop.security.dto.TokenResponseDTO;

import java.util.HashMap;
import java.util.Map;

//@Service
public class AuthService {
/*
   private final TokenService tokenService;
   private final UserDetailsService userService;
   private final BCryptPasswordEncoder passwordEncoder;

   private final Map<String, String> refreshStorage;

   public AuthService(TokenService tokenService, UserDetailsService userService, BCryptPasswordEncoder passwordEncoder) {
       this.tokenService = tokenService;
       this.userService = userService;
       this.passwordEncoder = passwordEncoder;
       this.refreshStorage = new HashMap<>();
   }

   public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) throws AuthException {
       String username = loginRequestDTO.username();

       UserDetails foundUser = userService.loadUserByUsername(username);

       if (!passwordEncoder.matches(loginRequestDTO.password(), foundUser.getPassword())) {
           String accessToken = tokenService.generateAccessToken(foundUser);
           String refreshToken = tokenService.generateRefreshToken(foundUser);

           return new TokenResponseDTO(accessToken, refreshToken);
       }

       throw new AuthException("Incorrect login and/ or password");
   }

   public TokenResponseDTO refreshToken(String refreshToken) throws AuthException {

       boolean isValid = tokenService.validateRefreshToken(refreshToken);

       Claims refreshClaims = tokenService.getRefreshClaimsFromToken(refreshToken);

       String username = refreshClaims.getSubject();
       String savedToken = refreshStorage.getOrDefault(username, null);
       boolean isSaved = savedToken != null && savedToken.equals(refreshToken);

       if (isValid && isSaved) {
           UserDetails foundUser = userService.loadUserByUsername(username);

           String accessToken = tokenService.generateAccessToken(foundUser);
           return new TokenResponseDTO(accessToken, refreshToken);

       }
               throw new AuthException("Invalid refresh token. Login again please");
   }

 */
}
