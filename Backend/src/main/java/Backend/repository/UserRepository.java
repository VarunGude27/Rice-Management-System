package Backend.repository;

import Backend.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Client,Long> {

    Client findFirstByEmail(String email);

    Optional<Client> findByFirstName(String firstName);

}
