package celebre.services;

import celebre.models.Teste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


import java.util.List;

@Service
public class TesteService {

    public List<Teste> obterProdutos() {
        List<Teste> list = new ArrayList<Teste>();
        list.add(new Teste("eae"));
        return list;
    }
}
