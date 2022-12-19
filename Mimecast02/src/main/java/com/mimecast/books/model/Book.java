package com.mimecast.books.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Book {
	@JsonProperty("book_title") private String name;
	@JsonProperty("summary") private String summary;
}
