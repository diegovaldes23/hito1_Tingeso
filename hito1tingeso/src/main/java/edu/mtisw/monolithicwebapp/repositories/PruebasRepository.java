package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PruebasRepository extends CrudRepository<PruebasEntity, Long> {

    // Consulta personalizada para encontrar pruebas por rut de estudiante
    @Query("select e from PruebasEntity e where e.rut_estudiante = :rut")
    ArrayList<PruebasEntity> findByRut(@Param("rut") String rut);
}
