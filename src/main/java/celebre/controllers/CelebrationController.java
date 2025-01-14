package celebre.controllers;

import celebre.helpers.Helpers;
import celebre.services.CelebrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
