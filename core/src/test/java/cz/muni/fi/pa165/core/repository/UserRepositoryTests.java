package cz.muni.fi.pa165.core.repository;

import cz.muni.fi.pa165.core.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");

        entityManager.persist(user1);
        entityManager.persist(user2);
    }

    @Test
    void testFindByEmailExistingEmail() {
        String email = "user1@example.com";

        Optional<User> foundUser = userRepository.findByEmail(email);

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    void testFindByEmailNonExistingEmail() {
        String email = "nonexisting@example.com";

        Optional<User> foundUser = userRepository.findByEmail(email);

        Assertions.assertFalse(foundUser.isPresent());
    }
}
