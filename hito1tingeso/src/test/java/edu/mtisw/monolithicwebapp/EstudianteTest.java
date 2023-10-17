package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.repositories.EstudiantesRepository;
import edu.mtisw.monolithicwebapp.services.EstudiantesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class EstudianteTest {
    @Autowired
    EstudiantesService estudiantesService;

    @Autowired
    EstudiantesRepository estudiantesRepository;


    @Test
    void saveEstudianteTest() {
        EstudiantesEntity estudiante = new EstudiantesEntity();

        estudiante.setRut("20.847.708-0");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setFecha_nacimiento("01/09/2001");
        estudiante.setTipo_colegio("Municipal");
        estudiante.setNombre_colegio("Liceo 2");
        estudiante.setAno_egreso(2020);
        estudiantesService.guardarEstudiantes(estudiante.getRut(), estudiante.getNombres(), estudiante.getApellidos(),
                estudiante.getFecha_nacimiento(), estudiante.getTipo_colegio(), estudiante.getNombre_colegio(),
                estudiante.getAno_egreso());

        assertNotEquals(new ArrayList<>(), estudiantesService.obtenerEstudiantes());
        estudiantesRepository.delete(estudiante);
    }

    @Test
    void findByRutTests() {
        EstudiantesEntity estudiante = new EstudiantesEntity();

        estudiante.setRut("20.847.708-0");
        estudiante.setNombres("Juan");
        estudiante.setApellidos("Valdes");
        estudiante.setFecha_nacimiento("01/09/2001");
        estudiante.setTipo_colegio("Municipal");
        estudiante.setNombre_colegio("Liceo 2");
        estudiante.setAno_egreso(2020);
        estudiantesService.guardarEstudiantes(
                estudiante.getRut(),
                estudiante.getNombres(),
                estudiante.getApellidos(),
                estudiante.getTipo_colegio(),
                estudiante.getNombre_colegio(),
                estudiante.getFecha_nacimiento(),
                estudiante.getAno_egreso());

        assertEquals(estudiante, estudiantesService.findByRut(estudiante.getRut()));
        estudiantesRepository.delete(estudiante);
    }

}
