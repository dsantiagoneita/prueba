package com.neita.neita.service;

import com.neita.neita.entity.Profesional;
import com.neita.neita.repository.ProfesionalRepository;
import com.neita.neita.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesionalServiceImpl implements ProfesionalService {

	@Autowired
	private ProfesionalRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository; // Inyectamos para validar usuario_id

	@Override
	public List<Profesional> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Profesional> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Profesional save(Profesional profesional) {
		// Lógica de negocio: Validar que el usuario asociado exista
		if (profesional.getUsuario() != null && !usuarioRepository.existsById(profesional.getUsuario().getId())) {
			throw new RuntimeException("El usuario asociado no existe.");
		}
		return repository.save(profesional);
	}

	@Override
	public Profesional update(Integer id, Profesional profesionalDetails) {
		return repository.findById(id).map(profesional -> {
			profesional.setEspecialidad(profesionalDetails.getEspecialidad());
			profesional.setHorarioDisponible(profesionalDetails.getHorarioDisponible());
			if (profesionalDetails.getUsuario() != null) {
				if (!usuarioRepository.existsById(profesionalDetails.getUsuario().getId())) {
					throw new RuntimeException("El usuario asociado no existe.");
				}
				profesional.setUsuario(profesionalDetails.getUsuario());
			}
			return repository.save(profesional);
		}).orElseThrow(() -> new RuntimeException("Profesional no encontrado con ID: " + id));
	}

	@Override
	public void deleteById(Integer id) {
		if (!repository.existsById(id)) {
			throw new RuntimeException("Profesional no encontrado con ID: " + id);
		}
		repository.deleteById(id);
	}
}