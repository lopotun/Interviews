package com.mimecast.books.services;

import com.mimecast.books.model.Book;

import java.util.List;

public interface BooksService {
	List<Book> getByAuthor(String author);
}
