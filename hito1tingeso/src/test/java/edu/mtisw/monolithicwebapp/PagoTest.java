package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.services.Pago;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class PagoTest {
    Pago pago = new Pago();
    EstudiantesEntity estudiante = new EstudiantesEntity();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");


    // TEST DE CALCULAR DESCUENTO COLEGIO
    //Calcular descuento por colegio Municipal con Cuotas
    @Test
    void calcularDescueuntoMunicipalCuotas() {
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Municipal");
        estudiante.setAno_egreso(2020);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota = 3;
        double nuevopago = pago.calcularDescuentoColegio(estudiante, cuota);
        assertEquals(300000, nuevopago, 0.0);
    }
    //Calcular descuento por colegio Subvencionado con Cuotas
    @Test
    void calcularDescueuntoSubvencionadoCuotas() {
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Subvencionado");
        estudiante.setAno_egreso(2020);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota = 3;
        double nuevopago = pago.calcularDescuentoColegio(estudiante, cuota);
        assertEquals(150000, nuevopago, 0.0);
    }
    //Calcular descuento por colegio Privado con Cuotas
    @Test
    void calcularDescueuntoPrivadoCuotas() {
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Privado");
        estudiante.setAno_egreso(2020);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota = 3;
        double nuevopago = pago.calcularDescuentoColegio(estudiante, cuota);
        assertEquals(0, nuevopago, 0.0);
    }

    //Calcular descuento por colegio Contado
    @Test
    void calcularDescueuntoContado() {
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Subvencionado");
        estudiante.setAno_egreso(2020);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota = 0;
        double nuevopago = pago.calcularDescuentoColegio(estudiante, cuota);
        assertEquals(750000, nuevopago, 0.0);
    }


    //CALCULAR FECHA DE VENCIMIENTO
    @Test
    void calcularFecha() {

        int mes = 1;
        String mesvence = pago.calcularFecha(mes);
        assertEquals("10/11/2023", mesvence);
    }

    // TEST DE CALCULAR DESCUENTO POR AÑO

    //Calcular descuento por año de egreso < 1
    @Test
    void calcularDescueuntoEgresoCuotas1() {
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Subvencionado");
        estudiante.setAno_egreso(2023);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota = 2;
        double nuevopago = pago.calcularDescuentoAno(estudiante, cuota);
        assertEquals(225000, nuevopago, 0.0);
    }

    //Calcular descuento por año de egreso mayor 1 y menor a 2
    @Test
    void calcularDescueuntoEgresoCuotas1_2() {
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Subvencionado");
        estudiante.setAno_egreso(2022);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota = 2;
        double nuevopago = pago.calcularDescuentoAno(estudiante, cuota);
        assertEquals(120000, nuevopago, 0.0);
    }

    //Calcular descuento por año de egreso mayor 3 y menor a 4
    @Test
    void calcularDescueuntoEgresoCuotas3_4() {
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Subvencionado");
        estudiante.setAno_egreso(2020);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota = 2;
        double nuevopago = pago.calcularDescuentoAno(estudiante, cuota);
        assertEquals(60000, nuevopago, 0.0);
    }

    //Calcular descuento por año en Contado
    @Test
    void calcularDescueuntoEgresoContado() {
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Subvencionado");
        estudiante.setAno_egreso(2022);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota = 0;
        double nuevopago = pago.calcularDescuentoAno(estudiante, cuota);
        assertEquals(0, nuevopago, 0.0);
    }

    //TEST de sumas de descuentos
    //Calcular descuento por año en Contado
    @Test
    void calcularDescueuntoAcumulable() {
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Subvencionado");
        estudiante.setAno_egreso(2022);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota = 0;
        double nuevopago = pago.calcularDescuentoAno(estudiante, cuota);
        double nuevopago2 = pago.calcularDescuentoColegio(estudiante, cuota);
        double pagofinal= pago.calcularDescuentoAcumulable(nuevopago,nuevopago2);
        assertEquals(750000, pagofinal, 0.0);
    }


    //TEST de la division de las cuotas
    @Test
    void calcularCuotas() {
        estudiante.setRut("11.345.678-2");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setNombre_colegio("Ruiz tagle");
        estudiante.setTipo_colegio("Municipal");
        estudiante.setAno_egreso(2023);
        estudiante.setFecha_nacimiento("2002-13-05");

        int cuota = 3;
        double nuevopago = pago.calcularDescuentoAno(estudiante, cuota);
        double nuevopago2 = pago.calcularDescuentoColegio(estudiante, cuota);
        double pagofinal= pago.calcularDescuentoAcumulable(nuevopago,nuevopago2);

        double division = pago.division(cuota,pagofinal);

        assertEquals(325000, division, 0);
    }



    @Test
    public void testCalcularDescuentoPuntajes() {
        // Preparar los ingredientes: crear nuevas entidades de prueba y sazonarlas con los valores necesarios
        PruebasEntity prueba1 = new PruebasEntity();
        prueba1.setFecha_examen("01/02/2023");
        prueba1.setPuntaje("950");

        PruebasEntity prueba2 = new PruebasEntity();
        prueba2.setFecha_examen("01/02/2023");
        prueba2.setPuntaje("960");

        ArrayList<PruebasEntity> pruebas = new ArrayList<>();
        pruebas.add(prueba1);
        pruebas.add(prueba2);

        // Otros ingredientes para la receta
        String vencimiento = "01/02/2023";
        int cantidad_cuotas = 3;  // Este valor no se usa en la función, pero lo incluyo por completitud
        double capital = 1000.0;

        // Cocinando la función y obteniendo el resultado
        double resultado = pago.calcularDescuentoPuntajes(pruebas, vencimiento, cantidad_cuotas, capital);

        // Verificar el sabor del resultado
        assertEquals(100, resultado, 0);
    }


    @Test
    void testCalcularDescuentoPuntajesRangoAlto() {
        ArrayList<PruebasEntity> pruebas = new ArrayList<>();
        PruebasEntity prueba = new PruebasEntity();
        prueba.setFecha_examen("01/02/2023");
        prueba.setPuntaje("960");
        pruebas.add(prueba);

        double descuento = pago.calcularDescuentoPuntajes(pruebas, "01/02/2023", 1, 1000);
        assertEquals(100, descuento, 0.0); // 10% de 1000 es 100.
    }
    @Test
    void testCalcularDescuentoPuntajesFechaMalFormateada() {
        ArrayList<PruebasEntity> pruebas = new ArrayList<>();
        PruebasEntity prueba = new PruebasEntity();
        prueba.setFecha_examen("01-02-2023"); // Usando guiones en lugar de barras.
        prueba.setPuntaje("960");
        pruebas.add(prueba);

        double descuento = pago.calcularDescuentoPuntajes(pruebas, "01/02/2023", 1, 1000);
        assertEquals(0.0, descuento, 0.0); // Debería devolver 0 debido al error.
    }


}
