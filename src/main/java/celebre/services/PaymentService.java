package celebre.services;

import celebre.models.PaymentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


import java.util.List;

@Service
public class PaymentService {

    public String payment() {
        return "payment";
    }
}
