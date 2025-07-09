package microservices.book.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/my-service")
    public ResponseEntity<Map<String, String>> myServiceFallback() {
        Map<String, String> response = new HashMap<>();
        // multiplcation/random용 response
        response.put("factorA", "죄송합니다, 서비스가 중단되었습니다!\n");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}