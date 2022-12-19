package net.kem.mimecast.persistence.repo;

import net.kem.mimecast.persistence.model.db.PublisherEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublisherMCRepository extends CrudRepository<PublisherEntity, Long> {
    List<PublisherEntity> findByName(String name);
}
