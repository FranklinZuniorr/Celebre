package celebre.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import celebre.entities.MessageResponseDto;
import celebre.entities.PaymentConfirmationProductBaseDto;
import celebre.enums.EnumEventType;
import celebre.entities.MetadataPaymentProductBaseDto;
import celebre.entities.PaymentCheckoutUrlDto;
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

            return helpers.<Object>generateResponse(HttpStatus.OK, celebration);
        } catch (Exception e) {
            System.out.println(e);
            return helpers.<Object>generateResponse(HttpStatus.BAD_REQUEST, new MessageResponseDto("A celebração não foi encontrada!"));
        }
    }
}
