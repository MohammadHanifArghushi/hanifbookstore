package fi.haagahelia.hanifbookstore;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.qos.logback.classic.Logger;
import fi.haagahelia.hanifbookstore.domain.Book;
import fi.haagahelia.hanifbookstore.domain.BookRepository;

@SpringBootApplication
public class HanifbookstoreApplication {
	private static final Logger log = (Logger) LoggerFactory.getLogger(HanifbookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HanifbookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository repository) {
		return (args) -> {

			log.info("save a couple of books");
			repository.save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 19.95));
			repository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 29.95));

			log.info("fetch all books");
			for (Book book : repository.findAll()) {
				log.info(book.toString());
			}
		};
	}

}
