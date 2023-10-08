package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.services.Pago;
import edu.mtisw.monolithicwebapp.services.ResumenService;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResumenTest {
    ResumenService resumenService = new ResumenService();

    EstudiantesEntity estudiante = new EstudiantesEntity();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    // TEST de ver tipo de pago Cuotas
    @Test
    void cuotascontado() {
        int forma = 3;
        String tipo = resumenService.verTipo(forma);
        assertEquals("Cuotas",tipo);
    }

    // TEST de ver tipo de pago Contado
    @Test
    void cuotascontado2() {
        int forma = 0;
        String tipo = resumenService.verTipo(forma);
        assertEquals("Contado",tipo);
    }


}
