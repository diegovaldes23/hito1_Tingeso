package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudiantesRepository extends CrudRepository<EstudiantesEntity, Long> {

    // Método para buscar un estudiante por su rut
    @Query("select e from EstudiantesEntity  e where e.rut = :rut")
    EstudiantesEntity findByRut(@Param("rut") String rut);
}
