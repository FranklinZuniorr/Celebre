package celebre.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import celebre.entities.MessageResponseDto;
import celebre.helpers.Helpers;
import celebre.models.Celebration;
import celebre.repositories.CelebrationRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class CelebrationService {

    @Autowired
    Helpers helpers;

    @Autowired
    CelebrationRepository celebrationRepository;

    public ResponseEntity<Object> getCelebration(String id) {
        try {
            Celebration celebration = celebrationRepository.getCelebration(id);
            Map<String, Celebration> payload = new HashMap<>();
            payload.put("celebration", celebration);

            return helpers.<Object>generateResponse(HttpStatus.OK, payload);
        } catch (Exception e) {
            System.out.println(e);
            return helpers.<Object>generateResponse(HttpStatus.BAD_REQUEST, new MessageResponseDto("A celebração não foi encontrada!"));
        }
    }
}
