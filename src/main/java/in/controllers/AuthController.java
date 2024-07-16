package in.controllers;

import dto.Massage;
import dto.TokenResponse;
import dto.User;
import dto.UserRequest;
import exceptions.PasswordNotEqualsException;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import secret.JwtUtils;
import services.AuthorizationService;

/**
 * Контроллер для авторизации
 */
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthorizationService authorizationService;
    private final JwtUtils jwtUtils;

    /**
     * Вход
     * @param userRequest
     * @return jwt-токен
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        try {
            User user = authorizationService.login(userRequest.getUsername(), userRequest.getPassword());
            return ResponseEntity.ok(new TokenResponse(jwtUtils.generateJwtToken(user.getUsername())));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(new Massage("Пользователь не найден."));
        } catch (PasswordNotEqualsException e) {
            return ResponseEntity.badRequest().body(new Massage("Пароль неправильный."));
        }
    }

    /**
     * Регистрация
     * @param userRequest
     * @return jwt-токен
     */
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        try {
            User register = authorizationService.register(userRequest.getUsername(), userRequest.getPassword());
            return ResponseEntity.ok(new TokenResponse(jwtUtils.generateJwtToken(register.getUsername())));
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(new Massage("Пользователь не найден."));
        }
    }
}
