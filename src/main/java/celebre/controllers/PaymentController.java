package celebre.controllers;

import celebre.entities.MessageResponseDto;
import celebre.entities.MetadataPaymentProductBaseDto;
import celebre.helpers.Helpers;
import celebre.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    Helpers helpers;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/checkout-product-base")
    public ResponseEntity<Object> checkoutProductBase(
        @RequestBody(required = true) String body
    ) {
        try {
            MetadataPaymentProductBaseDto metadataPaymentProductBaseDto = 
            helpers.normalizeJsonToObject(body, MetadataPaymentProductBaseDto.class);
            MetadataPaymentProductBaseDto metadataPaymentProductBase = new MetadataPaymentProductBaseDto(
                metadataPaymentProductBaseDto.getCelebrationTitle(), 
                metadataPaymentProductBaseDto.getPersonName(), 
                metadataPaymentProductBaseDto.getDescription(), 
                metadataPaymentProductBaseDto.getYoutubeUrl(), 
                metadataPaymentProductBaseDto.getEndPhrase(), 
                metadataPaymentProductBaseDto.getImageLink(), 
                metadataPaymentProductBaseDto.getEmail()
            );
            return paymentService.checkoutProductBase(metadataPaymentProductBase);
        } catch (Exception e) {
            return helpers.<Object>generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, new MessageResponseDto(e.getMessage()));
        }
    }

    @PostMapping("/hook-confirm-product-base-payment")
    public ResponseEntity<Object> hookConfirmProductBasePayment(@RequestBody String payload) {
        return paymentService.handlePaymentConfirmationProductBase(payload);
    }
}
