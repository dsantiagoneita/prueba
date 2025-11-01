package com.neita.neita.service;

import com.neita.neita.entity.Cita;
import com.neita.neita.repository.CitaRepository;
import com.neita.neita.repository.ProfesionalRepository;
import com.neita.neita.repository.ServicioRepository;
import com.neita.neita.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {

	@Autowired
	private CitaRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ServicioRepository servicioRepository;

	@Autowired
	private ProfesionalRepository profesionalRepository;

	@Override
	public List<Cita> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Cita> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Cita save(Cita cita) {
		// Lógica de negocio: Validar entidades asociadas
		if (cita.getUsuario() != null && !usuarioRepository.existsById(cita.getUsuario().getId())) {
			throw new RuntimeException("El usuario no existe.");
		}
		if (cita.getServicio() != null && !servicioRepository.existsById(cita.getServicio().getId())) {
			throw new RuntimeException("El servicio no existe.");
		}
		if (cita.getProfesional() != null && !profesionalRepository.existsById(cita.getProfesional().getId())) {
			throw new RuntimeException("El profesional no existe.");
		}
		// Chequear disponibilidad: No hay cita en el mismo horario para el profesional
		if (cita.getProfesional() != null && cita.getFechaHora() != null) {
			List<Cita> conflictos = repository.findByProfesionalIdAndFechaHora(cita.getProfesional().getId(),
					cita.getFechaHora());
			if (!conflictos.isEmpty()) {
				throw new RuntimeException("El horario ya está ocupado para este profesional.");
			}
		}
		// Setear estado por defecto si no está
		if (cita.getEstado() == null) {
			cita.setEstado("Pendiente");
		}
		return repository.save(cita);
	}

	@Override
	public Cita update(Integer id, Cita citaDetails) {
		return repository.findById(id).map(cita -> {
			cita.setFechaHora(citaDetails.getFechaHora());
			cita.setEstado(citaDetails.getEstado());
			if (citaDetails.getUsuario() != null) {
				if (!usuarioRepository.existsById(citaDetails.getUsuario().getId())) {
					throw new RuntimeException("El usuario no existe.");
				}
				cita.setUsuario(citaDetails.getUsuario());
			}
			if (citaDetails.getServicio() != null) {
				if (!servicioRepository.existsById(citaDetails.getServicio().getId())) {
					throw new RuntimeException("El servicio no existe.");
				}
				cita.setServicio(citaDetails.getServicio());
			}
			if (citaDetails.getProfesional() != null) {
				if (!profesionalRepository.existsById(citaDetails.getProfesional().getId())) {
					throw new RuntimeException("El profesional no existe.");
				}
				cita.setProfesional(citaDetails.getProfesional());
			}
			// Re-chequear disponibilidad en update si cambia fecha o profesional
			if (cita.getProfesional() != null && cita.getFechaHora() != null) {
				List<Cita> conflictos = repository.findByProfesionalIdAndFechaHora(cita.getProfesional().getId(),
						cita.getFechaHora());
				if (!conflictos.isEmpty() && !conflictos.get(0).getId().equals(id)) { // Ignorar la cita actual
					throw new RuntimeException("El horario ya está ocupado para este profesional.");
				}
			}
			return repository.save(cita);
		}).orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
	}

	@Override
	public void deleteById(Integer id) {
		if (!repository.existsById(id)) {
			throw new RuntimeException("Cita no encontrada con ID: " + id);
		}
		repository.deleteById(id);
	}
}