package udb.edu.sv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udb.edu.sv.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
}