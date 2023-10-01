package edu.mtisw.monolithicwebapp.controllers;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.services.PruebasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PruebasController {
    @Autowired
    PruebasService pruebasService;
    @GetMapping("/fileUpload")
    public String main() {
        return "fileUpload";
    }

    @GetMapping("/listarpruebas")
    public String listarpruebas(Model model) {
        ArrayList<PruebasEntity>pruebas= pruebasService.obtenerPruebas();
        model.addAttribute("pruebas",pruebas);
        return "index";
    }

    @GetMapping("/fileInformation")
    public String listar(Model model) {
        ArrayList<PruebasEntity> datas = pruebasService.obtenerPruebas();
        model.addAttribute("datas", datas);
        return "fileInformation";
    }


    @PostMapping("/fileUpload")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        pruebasService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        pruebasService.leerTxt("students_exams.csv");
        return "redirect:/fileUpload";
    }

}
