package celebre.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import celebre.models.Celebration;
import celebre.repositories.interfaces.InterfaceCelebrationRepository;

@Repository
public class CelebrationRepository {
    @Autowired
    private InterfaceCelebrationRepository interfaceCelebrationRepository;

    public void insertCelebration(Celebration celebration) {
        interfaceCelebrationRepository.insert(celebration);
    }
}
