package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository // Capa de Persistencia
public interface EstudiantesRepository extends CrudRepository<EstudiantesEntity, Long> {

    // Método para buscar estudiantes por nombres
    @Query("select e from EstudiantesEntity e where e.nombres = :nombres")
    EstudiantesEntity findByNameCustomQuery(@Param("nombres") String nombres);

    // Método para buscar el tipo de colegio de un estudiante por su rut
    @Query("select e.tipo_colegio from EstudiantesEntity  e where e.rut = :rut")
    String findByTipo_colegio(@Param("rut") String rut);

    // Método para buscar un estudiante por su rut
    @Query("select e from EstudiantesEntity  e where e.rut = :rut")
    EstudiantesEntity findByRut(@Param("rut") String rut);
}
