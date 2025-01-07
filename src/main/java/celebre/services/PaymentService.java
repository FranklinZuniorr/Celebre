package celebre.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import celebre.entities.MessageResponseDto;
import celebre.entities.PaymentCheckoutUrlDto;
import celebre.helpers.Helpers;

@Service
public class PaymentService {

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

    public ResponseEntity<Object> checkoutProductBase() {
        try {
            Stripe.apiKey = stripeApiKey;

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
                .build();
            Session session = Session.create(params);

            return helpers.<Object>generateResponse(HttpStatus.OK, new PaymentCheckoutUrlDto(session.getUrl()));
        } catch (Exception e) {
            System.out.println(e);
            return helpers.<Object>generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, new MessageResponseDto("Não foi possível gerar o link de pagamento!"));
        }
    }
}
