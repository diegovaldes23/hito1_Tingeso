package edu.mtisw.monolithicwebapp.controllers;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.services.PruebasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PruebasController {
    @Autowired
    PruebasService pruebasService;


    @GetMapping("/listarpruebas")
    public String listarpruebas(Model model) {
        ArrayList<PruebasEntity>pruebas= pruebasService.obtenerPruebas();
        model.addAttribute("pruebas",pruebas);
        return "index";
    }

}
