package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.entities.ResumenEntity;
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

        ArrayList<CuotasEntity> estudianteActualC= cuotasService.findRut(rut);

        EstudiantesEntity estudianteActual =estudiantesService.findByRut(rut);

        ArrayList<PruebasEntity> pruebaActual= pruebasService.findByRut(rut);


        ResumenEntity estudiante_reporte = new ResumenEntity();


        int cantidad_cuotas = buscarCantidad(estudianteActualC);

        estudiante_reporte.setRut(estudianteActual.getRut());
        estudiante_reporte.setNombre_estudiante(estudianteActual.getNombres());
        if (pruebaActual.isEmpty()) {
            estudiante_reporte.setExamen_rendido(0);
            estudiante_reporte.setPromedio_puntaje(0.0);
        } else {
            estudiante_reporte.setExamen_rendido(Contar(pruebaActual));
            estudiante_reporte.setPromedio_puntaje(Promedio(pruebaActual));
        }
        //puntajes
        estudiante_reporte.setMonto_totalA(pagoService.calcularDescuentoAcumulable(pagoService.calcularDescuentoColegio(estudianteActual,cantidad_cuotas),pagoService.calcularDescuentoAno(estudianteActual,cantidad_cuotas)));
        estudiante_reporte.setTipo_pagoCC(verTipo(cantidad_cuotas));
        estudiante_reporte.setN_total_cuotas_pactadas(cantidad_cuotas);
        estudiante_reporte.setN_cuotas_pagadas(calcularPagas(estudianteActualC));
        estudiante_reporte.setMonto_totalP(MontoPagar(estudianteActualC,pagoService.calcularDescuentoAcumulable(pagoService.calcularDescuentoColegio(estudianteActual,cantidad_cuotas),pagoService.calcularDescuentoAno(estudianteActual,cantidad_cuotas))));
        estudiante_reporte.setFecha_pago(calcularUltimoPago(estudianteActualC));
        estudiante_reporte.setSaldo_pagar(MontoNoPagar(estudianteActualC,pagoService.calcularDescuentoAcumulable(pagoService.calcularDescuentoColegio(estudianteActual,cantidad_cuotas),pagoService.calcularDescuentoAno(estudianteActual,cantidad_cuotas))));
        //Cuotas Retrasadas
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

    public double Promedio(ArrayList<PruebasEntity> pruebas) {
        double suma = 0.0;

        for (PruebasEntity prueba : pruebas) {
            // Convertir el puntaje de String a double antes de sumarlo
            double puntajeComoDouble = Double.parseDouble(prueba.getPuntaje());
            suma += puntajeComoDouble;  // Sumar los puntajes convertidos a double
        }

        // Calcular el promedio
        return suma / pruebas.size();
    }

    public int Contar(ArrayList<PruebasEntity> pruebas){
        long count = pruebas.stream()
                .count();

        // Convertir el conteo a un entero
        return (int) count;
    }
    public int buscarCantidad(ArrayList<CuotasEntity> cuotas) {
        if (cuotas != null && !cuotas.isEmpty()) {
            return cuotas.get(0).getCantidad_cuotas();
        } else {
            // Puedes manejar este caso como desees, en este ejemplo devuelvo 0
            return 0;
        }
    }
    public String verTipo(int cantidad_cuota){
        if (cantidad_cuota == 0){
            return "Contado";
        }else {
            return "Cuotas";
        }
    }

    public int calcularPagas(ArrayList<CuotasEntity> cuotas) {
        long count = cuotas.stream()
                .filter(cuota -> "Pagado".equals(cuota.getEstado()))
                .count();

        // Convertir el conteo a un entero
        return (int) count;
    }

    public double MontoPagar(List<CuotasEntity> cuotas, double arancelT) {
        double montoTotalPagar = 0.0;

        for (CuotasEntity cuota : cuotas) {
            if ("Pendiente".equals(cuota.getEstado())) {
                montoTotalPagar += cuota.getCapital();
            }
        }

        return arancelT - montoTotalPagar;
    }


    public double MontoNoPagar(List<CuotasEntity> cuotas, double arancelT) {
        double montoTotalNPagar = 0.0;

        for (CuotasEntity cuota : cuotas) {
            if ("Pagado".equals(cuota.getEstado())) {
                montoTotalNPagar += cuota.getCapital();
            }
        }

        // Restar el descuento adicional
        return arancelT - montoTotalNPagar;
    }


}
