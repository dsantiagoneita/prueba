package com.neita.neita.controller;

import com.neita.neita.entity.Servicio;
import com.neita.neita.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

	@Autowired
	private ServicioService service;

	@GetMapping
	public ResponseEntity<List<Servicio>> getAll() {
		List<Servicio> servicios = service.findAll();
		return ResponseEntity.ok(servicios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Servicio> getById(@PathVariable Integer id) {
		Optional<Servicio> servicio = service.findById(id);
		return servicio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Servicio> create(@RequestBody Servicio servicio) {
		try {
			Servicio saved = service.save(servicio);
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Servicio> update(@PathVariable Integer id, @RequestBody Servicio servicioDetails) {
		try {
			Servicio updated = service.update(id, servicioDetails);
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