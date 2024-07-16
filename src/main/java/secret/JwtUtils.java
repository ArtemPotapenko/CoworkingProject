package secret;

import dao.UserDao;
import dto.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

/**
 * Утилиты для работы с jwt
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final UserDao userDao;

    @Value("${jwt.token.secret}")
    private String jwtSecret;

    @Value("${jwt.token.expired}")
    private int jwtExpirationMs;

    /**
     * Создание токена
     * @param username
     * @return
     */
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256,jwtSecret)
                .compact();
    }

    /**
     * Получение имя пользователя
     * @param token
     * @return
     */
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Проверка токена
     * @param authToken
     * @return
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }

    /**
     * Получение токена из запроса
     * @param request
     * @return
     */

    public String extractJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
    /**
     * Польчение пользователя из запроса
     */
    public Optional<User> getUserFromJwtToken(HttpServletRequest request) {
        String authToken = extractJwtToken(request);
        if (authToken != null && validateJwtToken(authToken)) {
            return userDao.getUserByUsername(getUsernameFromJwtToken(authToken));
        }
        else {
            return Optional.empty();
        }
    }

}
