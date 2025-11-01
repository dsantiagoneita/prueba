package com.neita.neita.controller;

import com.neita.neita.entity.Usuario;
import com.neita.neita.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@GetMapping
	public ResponseEntity<List<Usuario>> getAll() {
		List<Usuario> usuarios = service.findAll();
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Integer id) {
		Optional<Usuario> usuario = service.findById(id);
		return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
		try {
			Usuario saved = service.save(usuario);
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(null); // Puedes retornar un mensaje de error
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuarioDetails) {
		try {
			Usuario updated = service.update(id, usuarioDetails);
			return ResponseEntity.ok(updated);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		try {
			service.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
}