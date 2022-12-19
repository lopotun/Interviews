package net.kem.mimecast;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import net.kem.mimecast.persistence.model.db.PublisherEntity;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import net.kem.mimecast.persistence.model.db.BookEntity;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT = "http://localhost:18081/api/";
    private static final String API_BOOK = API_ROOT + "books";
    private static final String API_PUBLISHER = API_ROOT + "publishers";

    @Test
    public void whenGetAllBooks_thenOK() {
        final Response response = RestAssured.get(API_BOOK);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetBooksByTitle_thenOK() {
        final BookEntity bookEntity = createRandomBook();
        createBookAsUri(bookEntity);

        final Response response = RestAssured.get(API_BOOK + "/title/" + bookEntity.getTitle());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
            .size() > 0);
    }

    @Test
    public void whenGetCreatedBookById_thenOK() {
        final BookEntity bookEntity = createRandomBook();
        final String location = createBookAsUri(bookEntity);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(bookEntity.getTitle(), response.jsonPath()
            .get("title"));
    }

    @Test
    public void whenGetNotExistBookById_thenNotFound() {
        final Response response = RestAssured.get(API_BOOK + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewBook_thenCreated() {
        final BookEntity bookEntity = createRandomBook();

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(bookEntity)
            .post(API_BOOK);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewPublisher_thenCreatedOLD() {
        final PublisherEntity publisherEntityLocal = createRandomPublisher();
        final String location = createPublisherAsUri(publisherEntityLocal);

        Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        PublisherEntity publisherEntityDB = response.as(PublisherEntity.class);

        final BookEntity bookEntity01 = createRandomBook(publisherEntityDB);
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bookEntity01)
                .post(API_BOOK);

        final BookEntity bookEntity02 = createRandomBook(publisherEntityDB);
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bookEntity02)
                .post(API_BOOK);

        final BookEntity bookEntity03 = createRandomBook(publisherEntityDB);
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bookEntity03)
                .post(API_BOOK);

        final BookEntity bookEntity04 = createRandomBook(publisherEntityDB);
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bookEntity04)
                .post(API_BOOK);

        response = RestAssured.get(API_BOOK + "/publisher/" + publisherEntityDB.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(4, Arrays.asList(response.getBody().as(BookEntity[].class)).size());
        assertTrue(((List<String>)response.jsonPath().get("author")).contains(bookEntity01.getAuthor()));
        assertTrue(((List<String>)response.jsonPath().get("author")).contains(bookEntity02.getAuthor()));
        assertTrue(((List<String>)response.jsonPath().get("author")).contains(bookEntity03.getAuthor()));
        assertTrue(((List<String>)response.jsonPath().get("author")).contains(bookEntity04.getAuthor()));
    }


    @Test
    public void whenInvalidBook_thenError() {
        final BookEntity bookEntity = createRandomBook();
        bookEntity.setAuthor(null);

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(bookEntity)
            .post(API_BOOK);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedBook_thenUpdated() {
        final BookEntity bookEntity = createRandomBook();
        final String location = createBookAsUri(bookEntity);

        bookEntity.setId(Long.parseLong(location.split("api/books/")[1]));
        bookEntity.setAuthor("newAuthor");
        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(bookEntity)
            .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newAuthor", response.jsonPath()
            .get("author"));

    }

    @Test
    public void whenDeleteCreatedBook_thenOk() {
        final BookEntity bookEntity = createRandomBook();
        final String location = createBookAsUri(bookEntity);

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // ===============================

    private BookEntity createRandomBook() {
        return createRandomBook(null);
    }
    private BookEntity createRandomBook(PublisherEntity publisherEntity) {
        final BookEntity bookEntity = new BookEntity("T_" + randomAlphabetic(10), "A_" + randomAlphabetic(15), publisherEntity);
//        book.setTitle(randomAlphabetic(10));
//        book.setAuthor(randomAlphabetic(15));
        return bookEntity;
    }

    private String createBookAsUri(BookEntity bookEntity) {
        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(bookEntity)
            .post(API_BOOK);
        return API_BOOK + "/" + response.jsonPath()
            .get("id");
    }

    private PublisherEntity createRandomPublisher() {
        final PublisherEntity publisherEntity = new PublisherEntity();
        publisherEntity.setName("Publisher_" + randomAlphabetic(5));
        return publisherEntity;
    }

    private String createPublisherAsUri(PublisherEntity publisherEntity) {
        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(publisherEntity)
            .post(API_PUBLISHER);
        return API_PUBLISHER + "/" + response.jsonPath()
            .get("id");
    }

}