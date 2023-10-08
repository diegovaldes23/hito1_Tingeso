package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.entities.ResumenEntity;
import edu.mtisw.monolithicwebapp.repositories.PruebasRepository;
import edu.mtisw.monolithicwebapp.repositories.ResumenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.List;

@Service
public class ResumenService {
    @Autowired
    ResumenRepository resumenRepository;

    @Autowired
    CuotasService cuotasService;

    @Autowired
    EstudiantesService estudiantesService;

    @Autowired
    Pago pagoService;

    @Autowired
    PruebasService pruebasService;

    public ArrayList<ResumenEntity> obtenerResumenes(){
        return (ArrayList<ResumenEntity>) resumenRepository.findAll();
    }


    public void calculoResumen(String rut) throws ParseException {
        // Obtener las cuotas del estudiante actual.
        ArrayList<CuotasEntity> estudianteActualC= cuotasService.findRut(rut);

        // Obtener los datos del estudiante actual.
        EstudiantesEntity estudianteActual = estudiantesService.findByRut(rut);

        // Obtener las pruebas del estudiante actual.
        ArrayList<PruebasEntity> pruebaActual = pruebasService.findByRut(rut);

        // Inicializar un nuevo objeto para almacenar el resumen del estudiante.
        ResumenEntity estudiante_reporte = new ResumenEntity();

        // Obtener la cantidad total de cuotas para este estudiante.
        int cantidad_cuotas = cuotasService.findCantidad(rut);

        // Configurar el resumen con los datos básicos del estudiante.
        estudiante_reporte.setRut(estudianteActual.getRut());
        estudiante_reporte.setNombre_estudiante(estudianteActual.getNombres());

        // Si no hay pruebas, setear examen rendido y promedio a 0.
        // De lo contrario, calcular el promedio.
        if (pruebaActual.isEmpty()) {
            estudiante_reporte.setExamen_rendido(0);
            estudiante_reporte.setPromedio_puntaje(0.0);
        } else {
            estudiante_reporte.setExamen_rendido(pruebaActual.size());
            estudiante_reporte.setPromedio_puntaje(pruebasService.findPromedioPuntaje(rut));
        }

        // Calcular el arancel real considerando descuentos.
        double arancelreal = pagoService.calcularDescuentoAcumulable(
                pagoService.calcularDescuentoColegio(estudianteActual, cantidad_cuotas),
                pagoService.calcularDescuentoAno(estudianteActual, cantidad_cuotas)
        );

        // Configurar el resumen con los datos financieros del estudiante.
        estudiante_reporte.setMonto_totalA(arancelreal);
        estudiante_reporte.setTipo_pagoCC(verTipo(cantidad_cuotas));
        estudiante_reporte.setN_total_cuotas_pactadas(cantidad_cuotas);
        estudiante_reporte.setN_cuotas_pagadas(cuotasService.findcantidadpagas(rut));
        estudiante_reporte.setFecha_pago(calcularUltimoPago(estudianteActualC));

        // Verificar si hay un monto total pagado y establecerlo. Si es null, usar 0.0.
        Double montoTotalPagado = cuotasService.findMontoTotalPagado(rut);
        if (montoTotalPagado == null) {
            montoTotalPagado = 0.0;
        }
        estudiante_reporte.setSaldo_pagar(arancelreal - montoTotalPagado);

        // Verificar si hay un monto total pendiente y establecerlo. Si es null, usar 0.0.
        Double montoTotalPendiente = cuotasService.findMontoTotalPendiente(rut);
        if (montoTotalPendiente == null) {
            montoTotalPendiente = 0.0;
        }
        estudiante_reporte.setMonto_totalP(arancelreal - montoTotalPendiente);

        // Guardar el resumen en la base de datos.
        resumenRepository.save(estudiante_reporte);
    }



    public String calcularUltimoPago(ArrayList<CuotasEntity> cuotas) {
        String ultimoPago = null;
        Date fechaUltimoPago = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (CuotasEntity cuota : cuotas) {
            if ("Pagado".equals(cuota.getEstado())) {
                try {
                    Date fechaPago = dateFormat.parse(cuota.getFecha_pago());

                    if (fechaUltimoPago == null || fechaPago.after(fechaUltimoPago)) {
                        fechaUltimoPago = fechaPago ;
                        ultimoPago = dateFormat.format(fechaPago);
                    }
                } catch (ParseException e) {
                    // Manejo de excepciones si hay problemas al analizar la fecha
                    e.printStackTrace();
                }
            }
        }

        return ultimoPago;
    }

    public String verTipo(int cantidad_cuota){
        if (cantidad_cuota == 0){
            return "Contado";
        }else {
            return "Cuotas";
        }
    }

}
