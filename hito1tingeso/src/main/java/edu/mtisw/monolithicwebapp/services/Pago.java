package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.repositories.CuotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Year;

import java.text.ParseException;
import java.util.ArrayList;

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

    @Autowired
    PruebasService pruebasService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public void calculoCuotas(String rut, int cantidad_cuotas) throws ParseException {

        EstudiantesEntity estudianteActual = estudiantesService.findByRut(rut);
        ArrayList<PruebasEntity> pruebaActual = pruebasService.findByRut(rut);

        // Inicializar el descuento acumulado
        double descuentoAcumulado = 0.0;

        if(cantidad_cuotas != 0){
            for (int i = 1; i <= cantidad_cuotas; i++) {
                CuotasEntity estudiante_reporteCuotas = new CuotasEntity();

                // Calcular el descuento acumulado para esta cuota
                double nuevo_arancel = division(cantidad_cuotas, calcularDescuentoAcumulable(calcularDescuentoColegio(estudianteActual, cantidad_cuotas), calcularDescuentoAno(estudianteActual, cantidad_cuotas)));
                double descuentoPep = calcularDescuentoPuntajes(pruebaActual, calcularFecha(i), cantidad_cuotas, nuevo_arancel);

                // Acumular el descuento
                descuentoAcumulado += descuentoPep;

                estudiante_reporteCuotas.setRut(estudianteActual.getRut());
                estudiante_reporteCuotas.setCantidad_cuotas(cantidad_cuotas);
                estudiante_reporteCuotas.setCapital(nuevo_arancel);
                estudiante_reporteCuotas.setDescuento_prueba(descuentoAcumulado); // Usar el descuento acumulado
                estudiante_reporteCuotas.setMulta(0.0);
                estudiante_reporteCuotas.setMonto_total(nuevo_arancel - descuentoAcumulado); // Restar el descuento acumulado
                estudiante_reporteCuotas.setEstado("Pendiente");
                estudiante_reporteCuotas.setFecha_vencimiento(calcularFecha(i));

                cuotasRepository.save(estudiante_reporteCuotas);
            }
        }else {
            CuotasEntity estudiante_reporteCuotas = new CuotasEntity();

            double nuevo_arancel = calcularDescuentoAcumulable( calcularDescuentoColegio(estudianteActual, cantidad_cuotas), calcularDescuentoAno(estudianteActual, cantidad_cuotas));
            double descuentoPep = calcularDescuentoPuntajes(pruebaActual, calcularFecha(1), cantidad_cuotas, nuevo_arancel);

            estudiante_reporteCuotas.setRut(estudianteActual.getRut());
            estudiante_reporteCuotas.setCantidad_cuotas(cantidad_cuotas);
            estudiante_reporteCuotas.setCapital(nuevo_arancel);
            estudiante_reporteCuotas.setDescuento_prueba(descuentoPep); // Usar el descuento acumulado
            estudiante_reporteCuotas.setMulta(0.0);
            estudiante_reporteCuotas.setMonto_total(nuevo_arancel - descuentoPep); // Restar el descuento acumulado
            estudiante_reporteCuotas.setEstado("Contado");

            cuotasRepository.save(estudiante_reporteCuotas);

        }
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

    public double calcularDescuentoAno(EstudiantesEntity estudiante,int cuota){

        double arancel = 1500000;
        double descuentoAnos = 0.0;

        if(cuota >0 ) {
            // Obtener el año actual
            int anoActual = Year.now().getValue();

            // Calcular descuento según años desde que egresó del colegio
            int cantidadAnos = anoActual - estudiante.getAno_egreso();
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

    //Suma de los descuentos acumulables

    public double calcularDescuentoAcumulable(double descuento1,double descuento2){
        double arancel = 1500000;
        return arancel - (descuento1 + descuento2);
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

    public static double calcularDescuentoPuntajes(ArrayList<PruebasEntity> pruebas, String vencimiento, int cantidad_cuotas, double capital) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Convertir la fecha de vencimiento a formato Date
            Date fechaVencimiento = dateFormat.parse(vencimiento);

            // Obtener el mes de la fecha de vencimiento
            int mesVencimiento = fechaVencimiento.getMonth() + 1; // Se suma 1 porque los meses comienzan desde 0

            double suma = 0.0;
            int contador = 0;

            for (PruebasEntity prueba : pruebas) {
                // Convertir la fecha de la prueba a formato Date
                Date fechaPrueba = dateFormat.parse(prueba.getFecha_examen());

                // Obtener el mes de la fecha de la prueba
                int mesPrueba = fechaPrueba.getMonth() + 1;

                //System.out.println("Mes de la prueba: " + mesPrueba);

                if (mesPrueba == mesVencimiento) {
                    //System.out.println("Entró");

                    // Convertir el puntaje a double
                    double puntaje = Double.parseDouble(prueba.getPuntaje());
                    suma += puntaje;
                    contador++;
                }
            }

            if (contador == 0) {
                //System.out.println("No hay pruebas en el mes.");
                return 0.0;
            }
            double puntajePromedio = suma / contador;

            double descuentoPuntaje = 0.0;
            if (puntajePromedio >= 950 && puntajePromedio <= 1000) {
                descuentoPuntaje = 0.10; // Descuento del 10% para puntajes entre 950 y 1000
            } else if (puntajePromedio >= 900 && puntajePromedio < 950) {
                descuentoPuntaje = 0.05; // Descuento del 5% para puntajes entre 900 y 949
            } else if (puntajePromedio >= 850 && puntajePromedio < 900) {
                descuentoPuntaje = 0.02; // Descuento del 2% para puntajes entre 850 y 899
            }
            double pagoFinal = capital * descuentoPuntaje;
            return pagoFinal;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

}
