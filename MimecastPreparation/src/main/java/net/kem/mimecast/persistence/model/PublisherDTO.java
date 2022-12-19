package net.kem.mimecast.persistence.model;

import lombok.Data;
import net.kem.mimecast.persistence.model.db.BookEntity;

import java.util.Set;

@Data
public class PublisherDTO {
	private String name;
	private Set<BookEntity> bookEntities;
}