package celebre.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import celebre.entities.MessageResponseDto;
import celebre.entities.UpdateCelebrationDto;
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

    public ResponseEntity<Object> getCelebrationProductBase(String id) {
        try {
            Celebration celebration = celebrationRepository.getCelebrationProductBase(id);
            Map<String, Celebration> payload = new HashMap<>();
            payload.put("celebration", celebration);

            return helpers.<Object>generateResponse(HttpStatus.OK, payload);
        } catch (Exception e) {
            return helpers.<Object>generateResponse(HttpStatus.BAD_REQUEST, new MessageResponseDto("A celebração não foi encontrada!"));
        }
    }

    public ResponseEntity<Object> putCelebrationProductBase(String id, UpdateCelebrationDto editedCelebration) {
        try {
            Celebration celebration = celebrationRepository.putCelebrationProductBase(id, editedCelebration);
            Map<String, Celebration> payload = new HashMap<>();
            payload.put("celebration", celebration);

            return helpers.<Object>generateResponse(HttpStatus.OK, payload); 
        } catch (Exception e) {
            return helpers.<Object>generateResponse(HttpStatus.BAD_REQUEST, new MessageResponseDto("Não foi possível editar a celebração!"));
        }
    }
}
