package net.kem.mimecast;

import net.kem.mimecast.persistence.model.db.BookEntity;
import net.kem.mimecast.persistence.model.db.PublisherEntity;
import net.kem.mimecast.persistence.repo.BookMCRepository;
import net.kem.mimecast.persistence.repo.PublisherMCRepository;
import net.kem.mimecast.web.PublisherController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MimecastPreparationTests {

	@Autowired
	private PublisherController publisherController;
	@Autowired
	private PublisherMCRepository publisherMCRepository;
	@Autowired
	private BookMCRepository bookMCRepository;

	@Test
	void contextLoads() {
		PublisherEntity publisherEntityL = new PublisherEntity();
		publisherEntityL.setName("Publisher_01");
		PublisherEntity publisherEntityR = publisherController.create(publisherEntityL);

		bookMCRepository.save(new BookEntity("T01", "A01", publisherEntityR));
		bookMCRepository.save(new BookEntity("T02", "A02", publisherEntityR));
		bookMCRepository.save(new BookEntity("T03", "A03", publisherEntityR));
		final BookEntity bookEntity = bookMCRepository.save(new BookEntity("T04", "A04", publisherEntityR));

		PublisherEntity publisherEntityN = publisherController.findByName("Publisher_01").get(0);
		System.out.println(publisherEntityN.getBooks());
		System.out.println(publisherEntityN);
	}

}
