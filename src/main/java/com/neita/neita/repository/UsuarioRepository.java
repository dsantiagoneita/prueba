package com.neita.neita.repository;

import com.neita.neita.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	// Ejemplo de método personalizado: buscar por email
	Optional<Usuario> findByEmail(String email);
}