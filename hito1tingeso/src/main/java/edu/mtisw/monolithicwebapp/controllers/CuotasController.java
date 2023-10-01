package edu.mtisw.monolithicwebapp.controllers;


import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.services.CuotasService;
import edu.mtisw.monolithicwebapp.services.Pago;

import edu.mtisw.monolithicwebapp.services.ResumenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
@RequestMapping
public class CuotasController {
    @Autowired
    CuotasService cuotasService;

    @Autowired
    Pago pagoService;

    @Autowired
    ResumenService resumenService;


    @GetMapping("/registro")
    public String registros() {
        return "cuotas";
    }
   @GetMapping("/listar-cuotas")
    public String listarC(@RequestParam(name="rut",required = false) String rut, Model model) {

        ArrayList<CuotasEntity> cuotas ;

        if (rut != null && !rut.isEmpty()){
            cuotas= cuotasService.findRut(rut);

       }else{
            cuotas=  cuotasService.findAll();

       }
        model.addAttribute("cuotas", cuotas);
        return "listadoCuotas";
    }


    // Método para manejar solicitudes POST a "/nuevo-estudiante" (crear un nuevo estudiante)
    @PostMapping("/cantidad-cuotas")
    public String nuevacuota(@RequestParam("rut") String rut,
                             @RequestParam("cantidad_cuotas") int cantidad_cuotas, RedirectAttributes redirectAttributes) throws ParseException {

        if (pagoService.maximoCuotasColegio(rut, cantidad_cuotas)){
            pagoService.calculoCuotas(rut,cantidad_cuotas);
            //resumenService.calculoResumen(rut,cantidad_cuotas);


        }else {
            System.err.println("No es correcta la cantidad de cuotas o no existe el estudiante");

            redirectAttributes.addFlashAttribute("mensajeC", "¡Cantidad de Cuotas Incorrecta!");}



        return "cuotas"; // Redirige a la página de cuotas
    }

    @PostMapping("/procesar-pago")
    public String pagando(@RequestParam("id") int id) throws ParseException{
        cuotasService.pagando(id);
        return "redirect:/listar-cuotas";

    }





}
