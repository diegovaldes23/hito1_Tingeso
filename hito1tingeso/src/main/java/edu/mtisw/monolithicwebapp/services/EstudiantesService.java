package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.repositories.EstudiantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service //Capa de Lógica de Negocios

public class EstudiantesService {
    @Autowired
    EstudiantesRepository estudiantesRepository;
    //Obtener todos los estudiantes
    public ArrayList<EstudiantesEntity> obtenerEstudiantes(){
        return (ArrayList<EstudiantesEntity>) estudiantesRepository.findAll();
    }
    //Guardar un estudiante
    public void guardarEstudiantes(String rut, String nombres, String apellidos, String tipo_colegio,String nombre_colegio,String fecha_nacimiento,int ano_egreso ){
        EstudiantesEntity estudiante = new EstudiantesEntity();
        estudiante.setRut(rut);
        estudiante.setNombres(nombres);
        estudiante.setApellidos(apellidos);
        estudiante.setTipo_colegio(tipo_colegio);
        estudiante.setNombre_colegio(nombre_colegio);
        estudiante.setFecha_nacimiento(fecha_nacimiento);
        estudiante.setAno_egreso(ano_egreso);
        estudiantesRepository.save(estudiante);
    }
    public EstudiantesEntity findByRut(String rut){
        return estudiantesRepository.findByRut(rut);
    }


}