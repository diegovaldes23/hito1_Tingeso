package edu.mtisw.monolithicwebapp.entities;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincremtar el id
    @Column(unique = true, nullable = false) //que sea el valor unico del id
    private Long id;

    private String rut;
    private String nombres;
    private String apellidos;
    private String tipo_colegio;
    private String nombre_colegio;
    private String fecha_nacimiento;
    private int ano_egreso;

}
