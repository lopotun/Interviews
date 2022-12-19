package net.kem.mimecast.persistence.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Evgeny Kurtser on 21-Nov-22 at 1:10 PM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
@Data
public class BookDTO {
	private Long id;
	@NotBlank(message = "Title cannot be blank")
	@Size(min = 2, message = "Title must be equal to or grater than 1 character")
	private String title;
	@NotBlank(message = "Author name cannot be blank")
	@Size(min = 2, message = "Author name must be equal to or grater than 2 characters")
	private String author;
}
