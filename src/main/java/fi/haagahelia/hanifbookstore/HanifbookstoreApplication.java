package fi.haagahelia.hanifbookstore;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import ch.qos.logback.classic.Logger;
import fi.haagahelia.hanifbookstore.domain.Book;
import fi.haagahelia.hanifbookstore.domain.BookRepository;
import fi.haagahelia.hanifbookstore.domain.Category;
import fi.haagahelia.hanifbookstore.domain.CategoryRepository;
import fi.haagahelia.hanifbookstore.domain.User;
import fi.haagahelia.hanifbookstore.domain.UserRepository;

@SpringBootApplication
public class HanifbookstoreApplication {
	private static final Logger log = (Logger) LoggerFactory.getLogger(HanifbookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HanifbookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository bookRepository, CategoryRepository categoryRepository,
			UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {

			Category fiction = new Category("Fiction");
			Category fantasy = new Category("Fantasy");
			Category horror = new Category("Horror");

			categoryRepository.save(fiction);
			categoryRepository.save(fantasy);
			categoryRepository.save(horror);

			bookRepository.save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 19.95, fiction));
			bookRepository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 29.95, fiction));

			String userPasswordHash = passwordEncoder.encode("userpassword");
			String adminPasswordHash = passwordEncoder.encode("adminpassword");

			User user1 = new User("user", userPasswordHash, "user@example.com", "USER");
			User user2 = new User("admin", adminPasswordHash, "admin@example.com", "ADMIN");

			userRepository.save(user1);
			userRepository.save(user2);

			log.info("Users initialized (user/userpassword, admin/adminpassword)");

			log.info("fetch all books");
			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}
		};
	}

}
