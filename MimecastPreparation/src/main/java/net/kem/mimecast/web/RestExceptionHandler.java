package net.kem.mimecast.web;

import net.kem.mimecast.web.exception.PublisherIdMismatchException;
import net.kem.mimecast.web.exception.PublisherNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.kem.mimecast.web.exception.BookIdMismatchException;
import net.kem.mimecast.web.exception.BookNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public RestExceptionHandler() {
        super();
    }

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> handleBookNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Book not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({
            BookIdMismatchException.class,
            PublisherIdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<Object> handleBookBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex
          .getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(PublisherNotFoundException.class)
    protected ResponseEntity<Object> handlePublisherNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Publisher not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}