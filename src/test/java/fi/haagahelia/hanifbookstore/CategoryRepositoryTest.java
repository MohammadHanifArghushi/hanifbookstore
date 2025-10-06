package fi.haagahelia.hanifbookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import fi.haagahelia.hanifbookstore.domain.Category;
import fi.haagahelia.hanifbookstore.domain.CategoryRepository;
import fi.haagahelia.hanifbookstore.service.UserDetailServiceImpl;
import fi.haagahelia.hanifbookstore.web.WebSecurityConfig;

@DataJpaTest
@Import(WebSecurityConfig.class)
public class CategoryRepositoryTest {

    @MockBean
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findCategoryByNameTest() {

        Category testCategory = new Category("Action");
        categoryRepository.save(testCategory);

        Iterable<Category> categories = categoryRepository.findAll();
        assertThat(categories).extracting(Category::getName).contains("Action");
    }

    @Test
    public void createNewCategoryTest() {
        Category newCategory = new Category("Romance");
        categoryRepository.save(newCategory);
        assertThat(newCategory.getId()).isNotNull();
    }

    @Test
    public void deleteCategoryTest() {
        Category categoryToDelete = new Category("Thriller");
        categoryRepository.save(categoryToDelete);
        Long idToDelete = categoryToDelete.getId();

        categoryRepository.delete(categoryToDelete);

        Optional<Category> deletedCategory = categoryRepository.findById(idToDelete);
        assertThat(deletedCategory).isEmpty();
    }
}