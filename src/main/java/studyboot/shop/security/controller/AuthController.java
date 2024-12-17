package studyboot.shop.security.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyboot.shop.security.dto.LoginRequestDTO;
import studyboot.shop.security.dto.RefreshRequestDTO;
import studyboot.shop.security.dto.TokenResponseDTO;
import studyboot.shop.security.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
/*
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            return authService.login(loginRequestDTO);
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken(@RequestBody RefreshRequestDTO refreshRequestDTO) {
        try {
            return authService.refreshToken(refreshRequestDTO.getRefreshToken());
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }

    }

 */
}
