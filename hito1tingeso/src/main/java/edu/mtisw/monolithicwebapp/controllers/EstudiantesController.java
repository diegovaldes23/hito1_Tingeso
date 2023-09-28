package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.services.EstudiantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;

// Capa de Presentación (Controlador)
@Controller // Indica que esta clase es un controlador de Spring MVC
@RequestMapping // Mapea las rutas para las solicitudes HTTP
public class EstudiantesController {
	@Autowired
	EstudiantesService estudiantesService; // Inyección de dependencias del servicio de estudiantes

	// Método para manejar solicitudes GET a "/listar-estudiantes"
	@GetMapping("/listar-estudiantes")
	public String listar(Model model) {
		// Obtiene la lista de estudiantes del servicio
		ArrayList<EstudiantesEntity> estudiantes = estudiantesService.obtenerEstudiantes();
		// Agrega la lista de estudiantes al modelo para su visualización en la vista
		model.addAttribute("estudiantes", estudiantes);
		return "index"; // Devuelve la vista "index" para mostrar la lista de estudiantes
	}

	// Método para manejar solicitudes GET a "/nuevo-estudiante" (página de creación de estudiantes)
	@GetMapping("/nuevo-estudiante")
	public String estudiante() {
		return "nuevo-estudiante"; // Devuelve la vista "nuevo-estudiante" para crear un nuevo estudiante
	}

	// Método para manejar solicitudes POST a "/nuevo-estudiante" (crear un nuevo estudiante)
	@PostMapping("/nuevo-estudiante")
	public String nuevoEstudiante(@RequestParam("rut") String rut,
								  @RequestParam("nombres") String nombres,
								  @RequestParam("apellidos") String apellidos,
								  @RequestParam("tipo_colegio") String tipo_colegio,
								  @RequestParam("nombre_colegio") String nombre_colegio,
								  @RequestParam("fecha_nacimiento") String fecha_nacimiento,
								  @RequestParam("ano_egreso") int ano_egreso) {
		// Llama al servicio para guardar el nuevo estudiante
		estudiantesService.guardarEstudiantes(rut, nombres, apellidos, tipo_colegio, nombre_colegio, fecha_nacimiento, ano_egreso);
		return "index"; // Redirige a la página de creación de estudiantes después de guardar
	}
}
