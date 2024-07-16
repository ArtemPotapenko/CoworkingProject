package in.controllers;

import dto.Space;
import dto.SpaceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import secret.JwtUtils;
import services.SpaceService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Контролер для рабочими местами
 */
@RestController
@RequiredArgsConstructor
public class SpaceController {
    private final SpaceService spaceService;
    private final JwtUtils jwtUtils;

    /**
     * Получение всех рабочих мест
     * @return
     */
    @GetMapping("/space")
    public List<Space> space() {
        return spaceService.getAllSpaces();
    }

    /**
     * Создание рабочего места
     * @param spaceRequest
     * @param request
     * @return
     */
    @PostMapping("/space")
    public ResponseEntity<?> createSpace(@RequestBody SpaceRequest spaceRequest, HttpServletRequest request) {
        if (jwtUtils.getUserFromJwtToken(request).isEmpty()) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
        Space space = spaceService.createSpace(spaceRequest.getSpaceName());
        return ResponseEntity.ok(space);
    }
}
