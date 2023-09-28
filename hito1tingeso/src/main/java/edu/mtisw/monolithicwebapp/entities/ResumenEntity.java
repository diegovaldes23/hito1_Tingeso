package edu.mtisw.monolithicwebapp.entities;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;

import javax.persistence.*;
@Entity
@Table(name = "resumen") // Conexion con el nombre de mysql
@Data //Getters y setters  librerias lombok
@NoArgsConstructor // Constructor con lombok
@AllArgsConstructor
public class ResumenEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String rut;
    private String nombre_estudiante;
    private int examen_rendido;
    private double promedio_puntaje;
    private double monto_totalA;
    private String tipo_pagoCC;
    private int n_total_cuotas_pactadas;
    private int n_cuotas_pagadas;
    private double monto_totalP;
    private String fecha_pago;
    private double saldo_pagar;
    private int n_cuotas_retraso;

}
