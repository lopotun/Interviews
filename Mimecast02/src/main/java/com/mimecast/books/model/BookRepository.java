package com.mimecast.books.model;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface BookRepository extends MongoRepository<BookDB, String> {

	//	@Query("{author:'?0'}")
//	BookDB findByAuthor(String name);
	BookDB findFirstByAuthor(String name);

	long count();
}