package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    User findByToken(String token);
}
