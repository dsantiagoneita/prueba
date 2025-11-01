package com.neita.neita.service;

import com.neita.neita.entity.Servicio;
import com.neita.neita.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

	@Autowired
	private ServicioRepository repository;

	@Override
	public List<Servicio> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Servicio> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Servicio save(Servicio servicio) {
		// Lógica de negocio: Validar precio positivo
		if (servicio.getPrecio() != null && servicio.getPrecio() < 0) {
			throw new RuntimeException("El precio no puede ser negativo.");
		}
		return repository.save(servicio);
	}

	@Override
	public Servicio update(Integer id, Servicio servicioDetails) {
		return repository.findById(id).map(servicio -> {
			servicio.setNombre(servicioDetails.getNombre());
			servicio.setDescripcion(servicioDetails.getDescripcion());
			servicio.setDuracion(servicioDetails.getDuracion());
			servicio.setPrecio(servicioDetails.getPrecio());
			// Validar precio en update también
			if (servicio.getPrecio() != null && servicio.getPrecio() < 0) {
				throw new RuntimeException("El precio no puede ser negativo.");
			}
			return repository.save(servicio);
		}).orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
	}

	@Override
	public void deleteById(Integer id) {
		if (!repository.existsById(id)) {
			throw new RuntimeException("Servicio no encontrado con ID: " + id);
		}
		repository.deleteById(id);
	}
}