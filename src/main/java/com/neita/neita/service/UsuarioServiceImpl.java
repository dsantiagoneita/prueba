package com.neita.neita.service;

import com.neita.neita.entity.Usuario;
import com.neita.neita.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public List<Usuario> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Usuario> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		// Lógica de negocio: Validar email único
		if (repository.findByEmail(usuario.getEmail()).isPresent()) {
			throw new RuntimeException("El email ya está registrado.");
		}
		// Setear fecha de registro automáticamente si no está seteada
		if (usuario.getFechaRegistro() == null) {
			usuario.setFechaRegistro(LocalDateTime.now());
		}
		// Aquí podrías hashear la password si agregas Spring Security
		return repository.save(usuario);
	}

	@Override
	public Usuario update(Integer id, Usuario usuarioDetails) {
		return repository.findById(id).map(usuario -> {
			usuario.setNombre(usuarioDetails.getNombre());
			usuario.setEmail(usuarioDetails.getEmail());
			usuario.setTelefono(usuarioDetails.getTelefono());
			// No actualizamos password ni fecha_registro por defecto; maneja por separado
			// si es necesario
			return repository.save(usuario);
		}).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
	}

	@Override
	public void deleteById(Integer id) {
		if (!repository.existsById(id)) {
			throw new RuntimeException("Usuario no encontrado con ID: " + id);
		}
		repository.deleteById(id);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return repository.findByEmail(email);
	}
}