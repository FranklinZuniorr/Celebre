package celebre.repositories.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;

import celebre.models.Celebration;

public interface InterfaceCelebrationRepository extends MongoRepository<Celebration, String> {}
