package com.example.sistema_parqueo.domain.port.output;

import com.example.sistema_parqueo.domain.entity.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<Usuario> findByUsernameOrEmail(String username, String email);
}