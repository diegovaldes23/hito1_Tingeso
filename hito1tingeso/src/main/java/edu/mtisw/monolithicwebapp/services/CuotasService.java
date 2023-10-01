package edu.mtisw.monolithicwebapp.services;


import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.repositories.CuotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    //Guardar una cuota
    public void guardarCuotas(String rut, int cantidad_cuota ){
        CuotasEntity cuotas = new CuotasEntity();
        cuotas.setRut(rut);
        cuotas.setCantidad_cuotas(cantidad_cuota);
        cuotasRepository.save(cuotas);
    }

    public void pagando(int id) {
        CuotasEntity cuota = cuotasRepository.findById(id);
        cuota.setEstado("Pagado");
        cuotasRepository.save(cuota);

    }

}
