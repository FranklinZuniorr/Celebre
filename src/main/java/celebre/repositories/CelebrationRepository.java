package celebre.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import celebre.models.Celebration;
import celebre.repositories.interfaces.InterfaceCelebrationRepository;
import jakarta.persistence.EntityNotFoundException;

@Repository
public class CelebrationRepository {
    @Autowired
    private InterfaceCelebrationRepository interfaceCelebrationRepository;

    public Celebration insertCelebration(Celebration celebration) {
        return interfaceCelebrationRepository.insert(celebration);
    }    

    public Celebration getCelebration(String id) {
        try {
            Optional<Celebration> optionalCelebration = interfaceCelebrationRepository.findById(id);
            
            if (optionalCelebration.isPresent()) {
                return optionalCelebration.get();
            } else {
                throw new EntityNotFoundException("Celebration com ID " + id + " não encontrada.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Celebration com ID " + id, e);
        }
    }
}
