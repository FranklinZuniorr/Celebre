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

import celebre.constants.HtmlPaymentConfirmation;
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
public class PaymentService {

    @Autowired
    Helpers helpers;

    @Autowired
    HtmlPaymentConfirmation constants;

    @Autowired
    CelebrationRepository celebrationRepository;

    @Value("${success.url}")
    private String successUrl;

    @Value("${cancel.url}")
    private String cancelUrl;

    @Value("${base.product.id}")
    private String baseProductId;

    @Value("${stripe.api.check.key}")
    private String stripeApiKey;

    @Value("${celebre.front.base.url}")
    private String celebreFrontBaseUrl;

    public ResponseEntity<Object> checkoutProductBase(MetadataPaymentProductBaseDto metadataPaymentProductBase) {
        try {
            System.out.println(metadataPaymentProductBase.getImageLink());
            Stripe.apiKey = stripeApiKey;
            String secureImageUrl = helpers.uploadImageBase64ToCloudinary(metadataPaymentProductBase.getImageLink());

            Map<String, String> metadata = new HashMap<>();
            metadata.put("celebrationTitle", metadataPaymentProductBase.getCelebrationTitle());
            metadata.put("personName", metadataPaymentProductBase.getPersonName());
            metadata.put("description", metadataPaymentProductBase.getDescription());
            metadata.put("youtubeUrl", metadataPaymentProductBase.getYoutubeUrl());
            metadata.put("endPhrase", metadataPaymentProductBase.getEndPhrase());
            metadata.put("imageLink", secureImageUrl);
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
            PaymentConfirmationProductBaseDto paymentConfirmationProductBaseDto = helpers.normalizeJsonToObject(
                payloadMetadataJson, PaymentConfirmationProductBaseDto.class);
            MetadataPaymentProductBaseDto metadata = paymentConfirmationProductBaseDto.getdata().getObject().getMetadata();

            EnumEventType eventType = paymentConfirmationProductBaseDto.getTypeFromString();

            switch (eventType) {
                case CHECKOUT_SESSION_COMPLETED:
                    Celebration newCelebration = new Celebration(
                        metadata.getCelebrationTitle(),
                        metadata.getPersonName(),
                        metadata.getDescription(),
                        metadata.getYoutubeUrl(),
                        metadata.getEndPhrase(),
                        metadata.getImageLink(),
                        metadata.getEmail()
                    );
                    Celebration celebration = celebrationRepository.insertCelebrationProductBase(newCelebration);
                    String email = constants.getHtmlPaymentConfirmation(celebreFrontBaseUrl + celebration.getId());

                    helpers.sendEmail(
                        metadata.getEmail(), 
                    "Compra realizada com sucesso!", 
                        email
                    );
                    break;
                default:
                    break;
            }

            return helpers.<Object>generateResponse(HttpStatus.OK, new MessageResponseDto("Evento processado com sucesso"));
        } catch (Exception e) {
            return helpers.<Object>generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, new MessageResponseDto("Erro ao processar o evento"));
        }
    }
}
