package celebre.repositories;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import celebre.entities.UpdateCelebrationDto;
import celebre.models.Celebration;
import celebre.repositories.interfaces.InterfaceCelebrationRepository;
import jakarta.persistence.EntityNotFoundException;

@Repository
public class CelebrationRepository {
    @Autowired
    private InterfaceCelebrationRepository interfaceCelebrationRepository;

    public Celebration insertCelebrationProductBase(Celebration celebration) {
        return interfaceCelebrationRepository.insert(celebration);
    }    

    public Celebration getCelebrationProductBase(String id) {
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

    public Celebration putCelebrationProductBase(String id, UpdateCelebrationDto editedCelebration) {
        try {

            Optional<Celebration> celebrationExistence = interfaceCelebrationRepository.findById(id);

        if (celebrationExistence.isPresent()) {
            Celebration celebration = celebrationExistence.get();
            celebration.setCelebrationTitle(
                Objects.requireNonNullElse(editedCelebration.getCelebrationTitle(), celebration.getCelebrationTitle())
            );
            celebration.setDescription(
                Objects.requireNonNullElse(editedCelebration.getDescription(), celebration.getDescription())
            );
            celebration.setEmail(
                Objects.requireNonNullElse(editedCelebration.getEmail(), celebration.getEmail())
            );
            celebration.setEndPhrase(
                Objects.requireNonNullElse(editedCelebration.getEndPhrase(), celebration.getEndPhrase())
            );
            celebration.setImageLink(
                Objects.requireNonNullElse(editedCelebration.getImageLink(), celebration.getImageLink())
            );
            celebration.setPersonName(
                Objects.requireNonNullElse(editedCelebration.getPersonName(), celebration.getPersonName())
            );
            celebration.setYoutubeUrl(
                Objects.requireNonNullElse(editedCelebration.getYoutubeUrl(), celebration.getYoutubeUrl())
            );

            return interfaceCelebrationRepository.save(celebration);
        } else {
            throw new EntityNotFoundException("Celebration com ID " + id + " não encontrada.");
        }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao editar Celebration com ID " + id, e);
        }
    }
}
