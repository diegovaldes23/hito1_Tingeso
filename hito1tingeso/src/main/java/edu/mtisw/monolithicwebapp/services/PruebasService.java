package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.repositories.PruebasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
public class PruebasService {
    @Autowired
    PruebasRepository pruebasRepository;

    //Obtener todos las pruebas
    public ArrayList<PruebasEntity> obtenerPruebas(){

        return (ArrayList<PruebasEntity>) pruebasRepository.findAll();
    }

    //Guardar una prueba
    public PruebasEntity guardarEstudiantes(PruebasEntity prueba){
        return pruebasRepository.save(prueba);
    }

    //Obtener prueba por id
    public Optional<PruebasEntity> obtenerPorId(Long id){
        return pruebasRepository.findById(id);
    }

    //Eliminar prueba por id
    public boolean eliminarPrueba(Long id) {
        try{
            pruebasRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }

}
