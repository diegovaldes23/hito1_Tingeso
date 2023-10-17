package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.services.CuotasService;
import edu.mtisw.monolithicwebapp.services.Pago;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CuotaTest {


    Pago pago = new Pago();

    CuotasService cuotasService= new CuotasService();
    CuotasEntity cuotaResumen= new CuotasEntity();
    EstudiantesEntity estudiante = new EstudiantesEntity();


    /*
    // TEST DE CAMBIAR DE PENDIENTE A PAGO
    @Test
    void cambiarPago() {
        cuotaResumen.setId(1);
        cuotaResumen.setRut("21.345.678-2");
        cuotaResumen.setCantidad_cuotas(3);
        cuotaResumen.setCapital(380000.0);
        cuotaResumen.setDescuento_prueba(19000.0);
        cuotaResumen.setMulta(0.0);
        cuotaResumen.setMonto_total(361000.0);
        cuotaResumen.setEstado("Pendiente");
        cuotaResumen.setFecha_vencimiento("10/11/2023");


        cuotasService.pagando(1);

        assertEquals("Pendiente", cuotaResumen.getEstado());
    }
*/

}
