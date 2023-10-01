package edu.mtisw.monolithicwebapp.repositories;


import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface  CuotasRepository extends CrudRepository<CuotasEntity,Long> {

    @Query("select e from CuotasEntity  e where e.rut = :rut")
    ArrayList<CuotasEntity> findRut(@Param("rut")String rut);

    @Query("select e from CuotasEntity  e where e.id = :id")
    CuotasEntity findById(@Param("id") int id);

}
