package com.mimecast.books.rest;

import com.mimecast.books.model.Book;
import com.mimecast.books.model.BookDB;
import com.mimecast.books.model.BookRepository;
import com.mimecast.books.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    private BooksService booksService;

    @PostMapping("author/{author}")
    public ResponseEntity<Void> createAuthor(@PathVariable String author) {
        final List<Book> bookList = booksService.getByAuthor(author);
        if(!bookList.isEmpty()) {
            bookRepository.save(new BookDB(author, bookList));
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("author/{author}")
    public ResponseEntity<List<? extends Book>> getByAuthor(@PathVariable String author) {
        BookDB res = bookRepository.findFirstByAuthor(author);  // DB may contain more than one books set with the same author.
        if(res == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(res.getBooks());
    }
}