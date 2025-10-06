package fi.haagahelia.hanifbookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import fi.haagahelia.hanifbookstore.domain.User;
import fi.haagahelia.hanifbookstore.domain.UserRepository;
import fi.haagahelia.hanifbookstore.service.UserDetailServiceImpl;
import fi.haagahelia.hanifbookstore.web.WebSecurityConfig;

@DataJpaTest
@Import(WebSecurityConfig.class)
public class UserRepositoryTest {

    @MockBean
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsernameShouldReturnUser() {

        User testUser = new User("testuser", "hash", "test@example.com", "USER");
        userRepository.save(testUser);

        User foundUser = userRepository.findByUsername("testuser");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void createNewUserTest() {
        User newUser = new User("newuser", "newhash", "new@example.com", "ADMIN");
        userRepository.save(newUser);
        assertThat(newUser.getId()).isNotNull();
    }

    @Test
    public void deleteUserTest() {
        User userToDelete = new User("todelete", "delhash", "del@example.com", "USER");
        userRepository.save(userToDelete);
        Long idToDelete = userToDelete.getId();

        userRepository.deleteById(idToDelete);

        Optional<User> deletedUser = userRepository.findById(idToDelete);
        assertThat(deletedUser).isEmpty();
    }
}