package net.kem.mimecast.persistence.model.db;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "publisher")
public class PublisherEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@OneToMany(mappedBy = "publisherEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<BookEntity> bookEntities = new LinkedHashSet<>();

	public Set<BookEntity> getBooks() {
		return bookEntities;
	}

//	public void addBook(Book book) {
//		this.books.add(book);
//	}

//	public void setBooks(Set<Book> books) {
//		this.books = books;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
}