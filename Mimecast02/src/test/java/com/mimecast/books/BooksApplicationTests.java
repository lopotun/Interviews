package com.mimecast.books;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mimecast.books.model.Book;
import com.mimecast.books.services.BooksService;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BooksApplicationTests {

	@Autowired
	private BooksService booksService;

	@Test
	void getAsResponse() {
		final List<Book> respMono = booksService.getByAuthor("Stephen King");
		System.out.println(respMono);
	}

	@Test
	void getAsResponseWithMongo(@Autowired MongoTemplate mongoTemplate) throws JsonProcessingException {
		String author = "Stephen King";
		List<Book> respMono = booksService.getByAuthor(author);

		// given
		DBObject objectToSave = BasicDBObjectBuilder.start()
				.add(author, respMono)
				.get();

		// when
		mongoTemplate.save(objectToSave, "collection");



		author = "Ellen Forney";
		respMono = booksService.getByAuthor(author);

		// given
		objectToSave = BasicDBObjectBuilder.start()
				.add(author, respMono)
				.get();

		// when
		mongoTemplate.save(objectToSave, "collection");

		// then
		assertThat(mongoTemplate.findAll(DBObject.class, "collection"))
				.extracting(author)
				.containsOnly(respMono);

		System.out.println(respMono);
	}

}
