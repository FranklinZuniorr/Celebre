package celebre.controllers;

import celebre.entities.MessageResponseDto;
import celebre.entities.MetadataPaymentProductBaseDto;
import celebre.helpers.Helpers;
import celebre.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    Helpers helpers;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/checkout-product-base")
    public ResponseEntity<Object> checkoutProductBase(
        @RequestParam(required = true) String celebrationTitle,
        @RequestParam(required = true) String personName,
        @RequestParam(required = true) String description,
        @RequestParam(required = true) String youtubeUrl,
        @RequestParam(required = true) String endPhrase,
        @RequestParam(required = true) String imageLink,
        @RequestParam(required = true) String email
    ) {
        try {
            MetadataPaymentProductBaseDto metadataPaymentProductBase = new MetadataPaymentProductBaseDto(
                celebrationTitle, personName, description, youtubeUrl, endPhrase, imageLink, email
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
