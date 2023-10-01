package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.repositories.CuotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.text.ParseException;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class Pago {

    @Autowired
    private CuotasRepository cuotasRepository;
    @Autowired
    private CuotasService cuotasService;

    @Autowired
    private EstudiantesService estudiantesService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public void calculoCuotas(String rut,int cantidad_cuotas) throws ParseException {

        EstudiantesEntity estudianteActual = estudiantesService.findByRut(rut);

        for (int i = 1; i <= cantidad_cuotas; i++){
            CuotasEntity estudiante_reporteCuotas = new CuotasEntity();
            estudiante_reporteCuotas.setRut(estudianteActual.getRut());
            estudiante_reporteCuotas.setCantidad_cuotas(cantidad_cuotas);
            estudiante_reporteCuotas.setCapital(division(cantidad_cuotas,calcularDescuentoAcumulable(calcularDescuentoColegio(estudianteActual,cantidad_cuotas),calcularDescuentoAno(estudianteActual,cantidad_cuotas))));
            //estudiante_reporteCuotas.setDescuento_prueba(calcularDescuentoPrueba(estudianteActual));
            //estudiante_reporteCuotas.setDescuento_multa(calcularDescuentoMulta(estudianteActual));
            //Monto total
            estudiante_reporteCuotas.setEstado("Pendiente");
            estudiante_reporteCuotas.setFecha_vencimiento(calcularFecha(i));
            //fecha

            cuotasRepository.save(estudiante_reporteCuotas);
        }
    }

    public String calcularFecha(int mes) {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();

        // Sumar el número de meses al calendario
        calendar.add(Calendar.MONTH, mes);

        // Establecer el día de vencimiento (10 en este caso)
        calendar.set(Calendar.DAY_OF_MONTH, 10);

        // Obtener la fecha resultante como String formateado
        return DATE_FORMAT.format(calendar.getTime());
    }
    //Método para calcular la cuota con el nuevo total de la resta del arancel y sus descuentos
    public double division(int cuota,double total){


        double div ;
        div = total / cuota;

        return div;
    }
    //Maximo de Cuotas por tipo de colegio

    public boolean maximoCuotasColegio(String rut,int cuota) {

        EstudiantesEntity estudiante = estudiantesService.findByRut(rut);
        int maximo = 0;
        if (estudiante != null){


            // Usamos equals para comparar el contenido de las cadenas
            if (estudiante.getTipo_colegio().equals("Municipal")) {
                maximo = 10;
            }
            if (estudiante.getTipo_colegio().equals("Subvencionado")) {
                maximo = 7;
            }
            if (estudiante.getTipo_colegio().equals("Privado")) {
                maximo = 4;
            }
        }else {
            return false;
        }



        // Verificar si la cantidad de cuotas está dentro del rango permitido

        return cuota >= 0 && cuota <= maximo;

    }



    public double calcularDescuentoColegio(EstudiantesEntity estudiante,int cuota ){
        double arancel = 1500000;
        double descuentoTipoColegio = 0.0;

        // Convertir el tipo de colegio ingresado a minúsculas para hacerlo insensible a mayúsculas/minúsculas
        String tipoColegioIngresado = estudiante.getTipo_colegio().toLowerCase();


        // Calcular descuento según Tipo de colegio de procedencia
        if (cuota > 0) {
            switch (tipoColegioIngresado) {
                case "municipal":
                    descuentoTipoColegio = 0.2; // 20% de descuento para colegio municipal
                    break;
                case "subvencionado":
                    descuentoTipoColegio = 0.1; // 10% de descuento para colegio subvencionado
                    break;
                case "privado":
                    descuentoTipoColegio = 0.0; // Sin descuento para colegio privado
                    break;
            }
        } else if (cuota == 0) {
            descuentoTipoColegio = 0.5; // 50% de descuento para pago al contado
        }

        return  arancel * descuentoTipoColegio;
    }

    public double calcularDescuentoAno(EstudiantesEntity estudiante,int cuota){

        double arancel = 1500000;
        double descuentoAnos = 0.0;

        if(cuota >0 ) {
            // Calcular descuento según años desde que egresó del colegio
            int cantidadAnos = 2023 - estudiante.getAno_egreso();
            if (cantidadAnos < 1) {
                descuentoAnos = 0.15; // 15% de descuento para menos de 1 año
            } else if (cantidadAnos >= 1 && cantidadAnos <= 2) {
                descuentoAnos = 0.08; // 8% de descuento para 1-2 años
            } else if (cantidadAnos >= 3 && cantidadAnos <= 4) {
                descuentoAnos = 0.04; // 4% de descuento para 3-4 años
            }
        }
        else if(cuota==0){
            descuentoAnos=0;
        }
        return arancel * descuentoAnos;
    }

    public double calcularDescuentoAcumulable(double descuento1,double descuento2){
        double arancel = 1500000;
        return arancel - (descuento1 + descuento2);
    }



    /*
    public double calcularDescuentoPuntajes(PruebasEntity prueba,String tipoPago, int arancelmes) {

        double descuentopuntaje = 0.0;
        double pagafinal=0.0;

        // Convertir el tipo de pago ingresado a minúsculas para hacerlo insensible a mayúsculas/minúsculas
        String tipoPagoIngresado = tipoPago.toLowerCase();

        // Calcular descuento según el puntaje
        int puntajeprom = prueba.getPuntaje();
        if (tipoPagoIngresado.equals("cuotas")) {

            if (puntajeprom < 850) {
                descuentopuntaje = 0.0;
            } else if (puntajeprom <= 899) {
                descuentopuntaje = 0.02;
            } else if (puntajeprom <= 949) {
                descuentopuntaje = 0.05;
            } else if (puntajeprom <= 1000) {
                descuentopuntaje = 0.1;
            }
        }else {
            //ESTA POR VERSE LA PARTE DEL CONTADO
        }

        pagafinal = arancelmes - (arancelmes*descuentopuntaje);
        return pagafinal;



    }*/




}
