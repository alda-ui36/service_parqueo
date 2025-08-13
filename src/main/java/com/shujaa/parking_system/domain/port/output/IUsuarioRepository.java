package com.shujaa.parking_system.domain.port.output;

import com.shujaa.parking_system.domain.entity.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    @EntityGraph(attributePaths = { "roles" })
    Optional<Usuario> findByUsernameOrEmail(String username, String email);
}