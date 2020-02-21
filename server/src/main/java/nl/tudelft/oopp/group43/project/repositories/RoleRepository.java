package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}