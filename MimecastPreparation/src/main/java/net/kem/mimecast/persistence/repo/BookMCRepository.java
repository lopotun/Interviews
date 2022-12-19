package net.kem.mimecast.persistence.repo;

import net.kem.mimecast.persistence.model.db.BookEntity;
import net.kem.mimecast.persistence.model.db.PublisherEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookMCRepository extends CrudRepository<BookEntity, Long> {
    List<BookEntity> findByTitle(String title);
    List<BookEntity> findByAuthorAndTitle(String author, String title);
    List<BookEntity> findByPublisherEntity(PublisherEntity publisherEntity);
}
