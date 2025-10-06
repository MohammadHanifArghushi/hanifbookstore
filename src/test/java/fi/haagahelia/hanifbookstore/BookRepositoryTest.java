package fi.haagahelia.hanifbookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import fi.haagahelia.hanifbookstore.domain.Book;
import fi.haagahelia.hanifbookstore.domain.BookRepository;
import fi.haagahelia.hanifbookstore.domain.Category;
import fi.haagahelia.hanifbookstore.domain.CategoryRepository;
import fi.haagahelia.hanifbookstore.service.UserDetailServiceImpl;

@DataJpaTest
@Import(fi.haagahelia.hanifbookstore.web.WebSecurityConfig.class)
public class BookRepositoryTest {

    @MockBean
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findAllBooksTest() {

        Category testCategory = new Category("TestCategory");
        categoryRepository.save(testCategory);
        bookRepository.save(new Book("Test Book", "Test Author", 2023, "1111111-1", 10.0, testCategory));

        Iterable<Book> books = bookRepository.findAll();
        assertThat(books).hasSizeGreaterThan(0);
    }

    @Test
    public void createNewBookTest() {
        Category testCategory = new Category("NewCategory");
        categoryRepository.save(testCategory);

        Book newBook = new Book("New Test Book", "A. Tester", 2024, "9999999-9", 50.0, testCategory);
        bookRepository.save(newBook);
        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void deleteBookTest() {
        Category testCategory = new Category("CategoryToDelete");
        categoryRepository.save(testCategory);
        Book bookToDelete = new Book("To Be Deleted", "D. E. Lete", 2000, "0000000-0", 5.0, testCategory);
        bookRepository.save(bookToDelete);
        Long idToDelete = bookToDelete.getId();

        bookRepository.deleteById(idToDelete);

        Optional<Book> deletedBook = bookRepository.findById(idToDelete);
        assertThat(deletedBook).isEmpty();
    }
}