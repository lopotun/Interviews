package net.kem.mimecast.web;

import net.kem.mimecast.persistence.model.db.PublisherEntity;
import net.kem.mimecast.persistence.repo.PublisherMCRepository;
import net.kem.mimecast.web.exception.PublisherIdMismatchException;
import net.kem.mimecast.web.exception.PublisherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherMCRepository publisherMCRepository;

    @GetMapping
    public Iterable<PublisherEntity> findAll() {
        return publisherMCRepository.findAll();
    }

    @Transactional
    @GetMapping("/name/{publisherTitle}")
    public List<PublisherEntity> findByName(@PathVariable String publisherTitle) {
        List<PublisherEntity> response = publisherMCRepository.findByName(publisherTitle);
        response.forEach(publisher -> System.out.println(publisher.getBooks()));
        return response;
    }

//    @GetMapping("/author-and-title/{bookAuthor}&{bookTitle}")
//    public List<Publisher> findByTitle(@PathVariable String bookAuthor, @PathVariable String bookTitle) {
//        return publisherRepository.findByAuthorAndTitle(bookAuthor, bookTitle);
//    }

    @GetMapping("/{id}")
    public PublisherEntity findOne(@PathVariable long id) {
        return publisherMCRepository.findById(id)
          .orElseThrow(PublisherNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherEntity create(@RequestBody PublisherEntity publisherEntity) {
        PublisherEntity publisherEntity1 = publisherMCRepository.save(publisherEntity);
        return publisherEntity1;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        publisherMCRepository.findById(id)
          .orElseThrow(PublisherNotFoundException::new);
        publisherMCRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public PublisherEntity updatePublisher(@RequestBody PublisherEntity publisherEntity, @PathVariable long id) {
        if (publisherEntity.getId() != id) {
            throw new PublisherIdMismatchException();
        }
        publisherMCRepository.findById(id)
          .orElseThrow(PublisherNotFoundException::new);
        return publisherMCRepository.save(publisherEntity);
    }
}
