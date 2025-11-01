package com.neita.neita.service;

import com.neita.neita.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
	List<Usuario> findAll();

	Optional<Usuario> findById(Integer id);

	Usuario save(Usuario usuario);

	Usuario update(Integer id, Usuario usuarioDetails);

	void deleteById(Integer id);

	Optional<Usuario> findByEmail(String email); // Método adicional para búsquedas
}