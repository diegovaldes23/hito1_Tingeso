package edu.mtisw.monolithicwebapp.services;


import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.repositories.CuotasRepository;
import edu.mtisw.monolithicwebapp.repositories.ResumenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CuotasService {
    @Autowired
    CuotasRepository cuotasRepository;

    //Obtener cuotas

    public ArrayList<CuotasEntity>findRut(String rut){
        return (ArrayList<CuotasEntity>)cuotasRepository.findRut(rut);
    }
    public ArrayList<CuotasEntity>findAll(){
        return (ArrayList<CuotasEntity>)cuotasRepository.findAll();
    }

    public int findcantidadpagas(String rut){return cuotasRepository.findcantidadpagas(rut);}

    public int findCantidad(String rut){return cuotasRepository.findCantidadCuotas(rut);}


    public  Double findMontoTotalPagado(String rut){return cuotasRepository.findMontoTotalPagado(rut);}
    public  Double findMontoTotalPendiente(String rut){return cuotasRepository.findMontoTotalPendiente(rut);}

    //Guardar una cuota
    public void pagando(int id) {
        CuotasEntity cuota = cuotasRepository.findById(id);

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Verificar si la fecha actual NO está entre el 5 y el 10 de cada mes
        if (fechaActual.getDayOfMonth() >=5  && fechaActual.getDayOfMonth() <= 10) {
            cuota.setEstado("Pagado");

            // Establecer la fecha de vencimiento como la fecha actual
            cuota.setFecha_pago(fechaActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            cuotasRepository.save(cuota);
        }
    }


}
