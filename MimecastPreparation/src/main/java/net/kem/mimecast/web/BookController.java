package net.kem.mimecast.web;

import net.kem.mimecast.persistence.model.BookDTO;
import net.kem.mimecast.persistence.model.db.BookEntity;
import net.kem.mimecast.persistence.model.db.PublisherEntity;
import net.kem.mimecast.persistence.repo.BookMCRepository;
import net.kem.mimecast.persistence.repo.PublisherMCRepository;
import net.kem.mimecast.web.exception.BookIdMismatchException;
import net.kem.mimecast.web.exception.BookNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookMCRepository bookMCRepository;
    @Autowired
    private PublisherMCRepository publisherMCRepository;

    @GetMapping
    public Iterable<BookEntity> findAll() {
        return bookMCRepository.findAll();
    }

    @GetMapping("/title/{bookTitle}")
    public List<BookEntity> findByTitle(@PathVariable String bookTitle) {
        return bookMCRepository.findByTitle(bookTitle);
    }

    @GetMapping("/author-and-title/{bookAuthor}&{bookTitle}")
    public List<BookEntity> findByTitle(@PathVariable String bookAuthor, @PathVariable String bookTitle) {
        return bookMCRepository.findByAuthorAndTitle(bookAuthor, bookTitle);
    }

    @GetMapping("/publisher/{publisherId}")
    public List<BookDTO> findByPublisher(@PathVariable long publisherId) {
        List<BookEntity> bookEntities = publisherMCRepository.findById(publisherId)
                .map(publisher -> bookMCRepository.findByPublisherEntity(publisher))
                .orElse(Collections.emptyList());

        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final List<BookDTO> res = bookEntities.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        return res;
    }

    @GetMapping("/{id}")
    public BookEntity findOne(@PathVariable long id) {
        return bookMCRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO book) {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        final BookEntity bookEntity = modelMapper.map(book, BookEntity.class);
        bookEntity.setPublisherEntity(new PublisherEntity());
        final BookEntity createdBook = bookMCRepository.save(bookEntity);
        final BookDTO res = modelMapper.map(createdBook, BookDTO.class);
        return res;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        bookMCRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        bookMCRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public BookEntity updateBook(@RequestBody BookEntity bookEntity, @PathVariable long id) {
        if (bookEntity.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookMCRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        return bookMCRepository.save(bookEntity);
    }
}
