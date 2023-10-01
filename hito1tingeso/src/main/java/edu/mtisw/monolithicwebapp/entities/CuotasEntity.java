package edu.mtisw.monolithicwebapp.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Entity
@Table(name = "cuotas") // Conexion con el nombre de mysql
@Data //Getters y setters  librerias lombok
@NoArgsConstructor // Constructor con lombok
@AllArgsConstructor
public class CuotasEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String rut;
    private int cantidad_cuotas;
    private double capital;
    private double descuento_prueba;
    private double multa;
    private double monto_total;
    private String estado;
    private String fecha_vencimiento;
}
