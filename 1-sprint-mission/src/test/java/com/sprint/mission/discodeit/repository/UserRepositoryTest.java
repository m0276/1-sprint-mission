package com.sprint.mission.discodeit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EntityManager entityManager;

  @Test
  void saveAndFindUser_Success() {
    // given
    User user = new User("testUser", "test@example.com", "password", null);
    User savedUser = userRepository.save(user);
    entityManager.flush();
    entityManager.clear();

    // when
    Optional<User> foundUser = userRepository.findById(savedUser.getId());

    // then
    assertThat(foundUser).isPresent();
    assertThat(foundUser.get().getUsername()).isEqualTo("testUser");
  }

  @Test
  void findByEmail_Fail_WhenUserDoesNotExist() {
    // when
    Optional<User> foundUser = userRepository.findByEmail("notfound@example.com");

    // then
    assertThat(foundUser).isEmpty();
  }
}
