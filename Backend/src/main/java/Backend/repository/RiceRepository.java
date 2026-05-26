package Backend.repository;

import Backend.entites.Rice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiceRepository extends JpaRepository<Rice, Long> {

    Optional<Rice> findByName(String name);
}
