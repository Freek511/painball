package freek.anotheratempt.Repo;

import freek.anotheratempt.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User getByEmail(String email);
    User findByName(String name);
    Optional<User> findByEmail(String email);
}
