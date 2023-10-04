package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.ResumenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResumenRepository extends CrudRepository<ResumenEntity,Long> {
}
