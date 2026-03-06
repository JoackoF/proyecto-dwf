package udb.edu.sv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udb.edu.sv.entity.Aircraft;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}