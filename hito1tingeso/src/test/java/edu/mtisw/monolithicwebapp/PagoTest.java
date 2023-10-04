package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.services.Pago;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagoTest {
    Pago pago = new Pago();
    EstudiantesEntity estudiante = new EstudiantesEntity();

    //Ver maximo de cuotas por tipo de colegio
    /*
    @Test
    void maximoCuotasColegioMuncipal(){
        estudiante.setRut("12.345.678-2");
        estudiante.setNombres("Raul");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Municipal");
        estudiante.setAno_egreso(2001);
        estudiante.setFecha_nacimiento("2001-23-05");

        int cuota=5;
        boolean maximo = pago.maximoCuotasColegio(,cuota);
        assertEquals(true,maximo);
    }
  */
    //Ver descuentos de entrada dependiendo de tipo de colegio

    //Ver descuentos de entrada dependiendo de tipo de colegio al contado
    @Test
    void calcularDescueuntoColegio1(){
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Municipal");
        estudiante.setAno_egreso(2020);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota= 3;
        double nuevopago = pago.calcularDescuentoColegio(estudiante,cuota);
        assertEquals(300000,nuevopago,0.0);
    }

    //Ver descuentos de entrada dependiendo de tipo de colegio con cuotas y privado

    @Test
    void calcularDescueuntoColegio2(){
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Privado");
        estudiante.setAno_egreso(2002);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota= 1;
        double nuevopago = pago.calcularDescuentoColegio(estudiante,cuota);
        assertEquals(1500000,nuevopago,0.0);
    }


    // Test de calcular descuento por año en cuotas
    @Test
    void calcularDescueuntoAno1(){
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Privado");
        estudiante.setAno_egreso(2022);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota= 2;
        double nuevopago = pago.calcularDescuentoAno(estudiante,cuota);
        assertEquals(1500000,nuevopago,0.0);
    }

    @Test
    void calcularDescueuntoAcumulable1(){
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Privado");
        estudiante.setAno_egreso(2022);
        estudiante.setFecha_nacimiento("2002-13-05");

        double descuento1 = 750000;
        double descuento2 = 0;
        double nuevopago = pago.calcularDescuentoAcumulable(descuento1,descuento2);
        assertEquals(750000,nuevopago,0.0);
    }





}
