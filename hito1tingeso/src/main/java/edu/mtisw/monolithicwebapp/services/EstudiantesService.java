package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.repositories.EstudiantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service //CAPA LOGICA DE NEGOCIO
public class EstudiantesService {
    @Autowired
    EstudiantesRepository estudiantesRepository;

    //Obtener todos los estudiantes
    public ArrayList<EstudiantesEntity> obtenerEstudiantes(){

        return (ArrayList<EstudiantesEntity>) estudiantesRepository.findAll();
    }

    //Guardar un estudiante
    public EstudiantesEntity guardarEstudiantes(EstudiantesEntity estudiante){
        return estudiantesRepository.save(estudiante);
    }

    //Obtener estudiante por id
    public Optional<EstudiantesEntity> obtenerPorId(Long id){
        return estudiantesRepository.findById(id);
    }

    //Eliminar estudiante por id
    public boolean eliminarEstudiante(Long id) {
        try{
            estudiantesRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
  
}