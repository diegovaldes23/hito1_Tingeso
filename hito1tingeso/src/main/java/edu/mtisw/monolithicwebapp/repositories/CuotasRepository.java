package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;


@Repository
public interface CuotasRepository extends CrudRepository<CuotasEntity, Long> {

    // Consulta personalizada para encontrar cuotas por rut
    @Query("select e from CuotasEntity e where e.rut = :rut")
    ArrayList<CuotasEntity> findRut(@Param("rut") String rut);

    // Consulta personalizada para encontrar cuota por id
    @Query("select e from CuotasEntity e where e.id = :id")
    CuotasEntity findById(@Param("id") int id);

    //Contar cantidad de cuotas
    @Query("select distinct e.cantidad_cuotas from CuotasEntity e where e.rut = :rut")
    int findCantidadCuotas(@Param("rut") String rut);

    //Contar cantidad de cuotas pagadas
    @Query("select count(e) from CuotasEntity e where e.rut = :rut and e.estado = 'Pagado'")
    int findcantidadpagas(@Param("rut") String rut);

    @Query("select sum(e.capital) from CuotasEntity e where e.rut = :rut and e.estado = 'Pagado'")
    Double findMontoTotalPagado(@Param("rut") String rut);

    @Query("select sum(e.capital) from CuotasEntity e where e.rut = :rut and e.estado = 'Pendiente'")
    Double findMontoTotalPendiente(@Param("rut") String rut);



}
