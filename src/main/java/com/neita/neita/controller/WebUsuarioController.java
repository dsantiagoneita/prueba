package com.neita.neita.controller;

import com.neita.neita.entity.Usuario;
import com.neita.neita.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class WebUsuarioController {

	@Autowired
	private UsuarioService service;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("usuarios", service.findAll());
		return "usuarios/list";
	}

	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuarios/form";
	}

	@PostMapping
	public String save(@ModelAttribute("usuario") Usuario usuario) {
		service.save(usuario);
		return "redirect:/usuarios";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Integer id, Model model) {
		Usuario usuario = service.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		model.addAttribute("usuario", usuario);
		return "usuarios/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Integer id, @ModelAttribute("usuario") Usuario usuario) {
		service.update(id, usuario);
		return "redirect:/usuarios";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		service.deleteById(id);
		return "redirect:/usuarios";
	}
}