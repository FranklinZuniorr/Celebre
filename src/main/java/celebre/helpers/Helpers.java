package celebre.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Helpers {
    public <T> ResponseEntity<T> generateResponse(HttpStatus status, T body) {
        return new ResponseEntity<>(body, status);
    }
}