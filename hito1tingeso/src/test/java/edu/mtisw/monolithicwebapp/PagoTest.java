package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.services.Pago;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagoTest {
    Pago pago = new Pago();
    EstudiantesEntity estudiante = new EstudiantesEntity();

    //Ver maximo de cuotas por tipo de colegio
    @Test
    void maximoCuotasColegioMuncipal(){
        estudiante.setRut("12.345.678-2");
        estudiante.setNombres("Raul");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Municipal");
        estudiante.setAno_egreso(2001);
        estudiante.setFecha_nacimiento("2001-23-05");

        int maximo = pago.maximoCuotasColegio(estudiante);
        assertEquals(10,maximo,0.0);
    }

    //Ver descuentos de entrada dependiendo de tipo de colegio, en contado
    @Test
    void desceuntosdeentradaContado(){
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Privado");
        estudiante.setAno_egreso(2002);
        estudiante.setFecha_nacimiento("2002-13-05");

        double nuevopago = pago.descuentoentrada(estudiante,"contado");
        assertEquals(750000,nuevopago,0.0);
    }

    //Ver descuentos de entrada dependiendo de tipo de colegio, en cuotas

    @Test
    void desceuntosdeentradaCuotas(){
        estudiante.setRut("20.345.678-2");
        estudiante.setNombres("Bastian");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("San jose");
        estudiante.setTipo_colegio("Municipal");
        estudiante.setAno_egreso(2002);
        estudiante.setFecha_nacimiento("2002-10-05");

        double nuevopago = pago.descuentoentrada(estudiante,"cuotas");
        assertEquals(1200000,nuevopago,0.0);
    }


}
