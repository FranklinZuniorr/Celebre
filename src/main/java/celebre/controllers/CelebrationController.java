package celebre.controllers;

import celebre.entities.MessageResponseDto;
import celebre.entities.MetadataPaymentProductBaseDto;
import celebre.entities.UpdateCelebrationDto;
import celebre.helpers.Helpers;
import celebre.services.CelebrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/celebration")
public class CelebrationController {

    @Autowired
    Helpers helpers;

    @Autowired
    private CelebrationService celebrationService;

    @GetMapping("/product-base/{id}")
    public ResponseEntity<Object> getCelebrationProductBase(@PathVariable(required = true) String id) {
        return celebrationService.getCelebrationProductBase(id);
    }

    @PutMapping("/product-base/{id}")
    public ResponseEntity<Object> putCelebrationProductBase(
        @PathVariable String id,
        @RequestBody(required = true) String body
    ) {

        try {
            UpdateCelebrationDto celebrationDataUpdate = 
            helpers.normalizeJsonToObject(body, UpdateCelebrationDto.class);

            UpdateCelebrationDto updateCelebrationDto = new UpdateCelebrationDto(
                celebrationDataUpdate.getCelebrationTitle(), 
                celebrationDataUpdate.getPersonName(), 
                celebrationDataUpdate.getDescription(), 
                celebrationDataUpdate.getYoutubeUrl(), 
                celebrationDataUpdate.getEndPhrase(), 
                celebrationDataUpdate.getImageLink(), 
                celebrationDataUpdate.getEmail()
            );
            return celebrationService.putCelebrationProductBase(id, updateCelebrationDto);
        } catch (Exception e) {
            return helpers.<Object>generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, new MessageResponseDto(e.getMessage()));
        }
    }
}
