package edu.mtisw.monolithicwebapp.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "estudiantes") // Conexion con el nombre de mysql
@Data //Getters y setters  librerias lombok
@NoArgsConstructor // Constructor con lombok
@AllArgsConstructor
public class EstudiantesEntity {

    @Id
    @NotNull

    private String rut;
    private String nombres;
    private String apellidos;
    private String tipo_colegio;
    private String nombre_colegio;
    private String fecha_nacimiento;
    private int ano_egreso;

}
