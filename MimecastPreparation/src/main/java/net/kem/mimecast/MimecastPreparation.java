package net.kem.mimecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories("net.kem.mimecast.persistence.repo")
@EntityScan("net.kem.mimecast.persistence.model")
@SpringBootApplication
public class MimecastPreparation {

	public static void main(String[] args) {
		SpringApplication.run(MimecastPreparation.class, args);
	}

}
