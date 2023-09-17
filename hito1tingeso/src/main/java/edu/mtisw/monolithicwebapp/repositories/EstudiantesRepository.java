package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository //CAPA DE PERSISTENCIA
public interface EstudiantesRepository extends CrudRepository<EstudiantesEntity, Long> {

}