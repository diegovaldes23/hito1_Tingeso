package edu.mtisw.monolithicwebapp.entities;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincremtar el id
    @Column(unique = true, nullable = false) //que sea el valor unico del id
    private Long id;

    private String rut_estudiante;
    private String fecha_examen;
    private int puntaje;


}
