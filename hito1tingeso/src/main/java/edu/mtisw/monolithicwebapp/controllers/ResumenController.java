package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.ResumenEntity;
import edu.mtisw.monolithicwebapp.services.ResumenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping
public class ResumenController {
    @Autowired
    ResumenService resumenService;

    // Método para manejar resumenes GET a "/listar-estudiantes"
    @GetMapping("/listar-resumenes")
    public String listarR(Model model){
        ArrayList<ResumenEntity> resumenes = resumenService.obtenerResumenes();
        model.addAttribute("resumenes", resumenes);
        return "resumen";//Debo cambiar esto


    }






}
