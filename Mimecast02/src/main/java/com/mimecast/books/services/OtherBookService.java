package com.mimecast.books.services;

import com.mimecast.books.model.Book;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Service
@ConditionalOnProperty(prefix = "book", name = "service", havingValue = "other")
public class OtherBookService implements BooksService {
	public OtherBookService() {
	}

	public List<Book> getByAuthor(String author) {
		return IntStream.range(0, 10)
				.mapToObj(i -> new Book("Name_" + randomAlphabetic(5), "Summary_" + randomAlphabetic(5)))
				.collect(Collectors.toList());
	}
}