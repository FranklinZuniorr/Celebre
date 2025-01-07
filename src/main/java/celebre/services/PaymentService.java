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
import celebre.entities.MetadataPaymentProductBaseDto;
import celebre.entities.PaymentCheckoutUrlDto;
import celebre.helpers.Helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class PaymentService {

    private static final Logger logger = Logger.getLogger(PaymentService.class.getName());

    @Autowired
    Helpers helpers;

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

    public ResponseEntity<Object> handlePaymentConfirmationProductBase(String payload) {
        try {
            

            System.out.println(payload);
            return helpers.<Object>generateResponse(HttpStatus.OK, new MessageResponseDto("Evento processado com sucesso"));
        } catch (Exception e) {
            logger.severe("Error processing webhook: " + e.getMessage());
            return helpers.<Object>generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, new MessageResponseDto("Erro ao processar o evento"));
        }
    }

    /* private void handlePaymentIntentSucceeded(PaymentIntent paymentIntent) {
        // Lógica para quando o PaymentIntent for bem-sucedido
        logger.info("PaymentIntent succeeded: " + paymentIntent.getId());
        // Aqui você pode atualizar o estado do pedido, notificar o cliente, etc.
    } */
}
