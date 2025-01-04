package celebre.controllers;

import celebre.services.TesteService;
import celebre.models.Teste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private TesteService testeService;

    @GetMapping
    public List<Teste> listarProdutos() {
        return testeService.obterProdutos();
    }
}
