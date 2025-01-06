package celebre.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import celebre.helpers.Helpers;

@Service
public class PaymentService {

    @Autowired
    Helpers helpers;

    public ResponseEntity<String> payment() {
        return helpers.<String>generateResponse(HttpStatus.OK, "Paymen success!");
    }
}
