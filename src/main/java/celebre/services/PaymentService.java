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
import java.util.logging.Logger;

@Service
public class PaymentService {

    private static final Logger logger = Logger.getLogger(PaymentService.class.getName());

    @Autowired
    Helpers helpers;

    @Autowired
    CelebrationRepository celebrationRepository;

    @Value("${success.url}")
    private String successUrl;

    @Value("${cancel.url}")
    private String cancelUrl;

    @Value("${base.product.id}")
    private String baseProductId;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public ResponseEntity<Object> checkoutProductBase(MetadataPaymentProductBaseDto metadataPaymentProductBase) {
        try {
            Stripe.apiKey = stripeApiKey;

            Map<String, String> metadata = new HashMap<>();
            metadata.put("celebrationTitle", metadataPaymentProductBase.getCelebrationTitle());
            metadata.put("personName", metadataPaymentProductBase.getPersonName());
            metadata.put("description", metadataPaymentProductBase.getDescription());
            metadata.put("youtubeUrl", metadataPaymentProductBase.getYoutubeUrl());
            metadata.put("endPhrase", metadataPaymentProductBase.getEndPhrase());
            metadata.put("imageLink", metadataPaymentProductBase.getImageLink());
            metadata.put("email", metadataPaymentProductBase.getEmail());

            SessionCreateParams params =
            SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPrice(baseProductId)
                    .build())
                    .putAllMetadata(metadata)
                .build();
            Session session = Session.create(params);

            return helpers.<Object>generateResponse(HttpStatus.OK, new PaymentCheckoutUrlDto(session.getUrl()));
        } catch (Exception e) {
            System.out.println(e);
            return helpers.<Object>generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, new MessageResponseDto("Não foi possível gerar o link de pagamento!"));
        }
    }

    public ResponseEntity<Object> handlePaymentConfirmationProductBase(String payloadMetadataJson) {
        try {
            Gson gson = new Gson();

            PaymentConfirmationProductBaseDto paymentConfirmationProductBaseDto = 
            gson.fromJson(payloadMetadataJson, PaymentConfirmationProductBaseDto.class);
            MetadataPaymentProductBaseDto metadata = paymentConfirmationProductBaseDto.getdata().getObject().getMetadata();

            EnumEventType eventType = paymentConfirmationProductBaseDto.getTypeFromString();

            switch (eventType) {
                case CHECKOUT_SESSION_COMPLETED:
                    helpers.sendEmail(metadata.getEmail(), "Compra realizada com sucesso!", "Aqui está a sua página: https...");

                    Celebration newCelebration = new Celebration(
                        metadata.getCelebrationTitle(),
                        metadata.getPersonName(),
                        metadata.getDescription(),
                        metadata.getYoutubeUrl(),
                        metadata.getEndPhrase(),
                        metadata.getImageLink(),
                        metadata.getEmail()
                    );
                    celebrationRepository.insertCelebration(newCelebration);
                    break;
                default:
                    break;
            }

            return helpers.<Object>generateResponse(HttpStatus.OK, new MessageResponseDto("Evento processado com sucesso"));
        } catch (Exception e) {
            logger.severe("Error processing webhook: " + e.getMessage());
            return helpers.<Object>generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, new MessageResponseDto("Erro ao processar o evento"));
        }
    }
}
