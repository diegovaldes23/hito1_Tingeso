package edu.mtisw.monolithicwebapp.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "pruebas") // Conexion con el nombre de mysql
@Data //Getters y setters  librerias lombok
@NoArgsConstructor // Constructor con lombok
@AllArgsConstructor
public class PruebasEntity {
    @Id
    @NotNull

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String rut_estudiante;
    private String fecha_examen;
    private String puntaje;


}
