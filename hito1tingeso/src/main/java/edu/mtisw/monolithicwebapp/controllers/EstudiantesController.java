package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.services.EstudiantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
//CAPA DE MUESTRA
@Controller
@RequestMapping
public class EstudiantesController {
    @Autowired
	EstudiantesService estudiantesService;

    @GetMapping("/listar")
	public String listar(Model model) {
    	ArrayList<EstudiantesEntity>estudiantes= estudiantesService.obtenerEstudiantes();
    	model.addAttribute("estudiantes",estudiantes);
		return "index";
	}

}