package edu.mtisw.monolithicwebapp.entities;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "resumen")    // Nombre de la tabla en la base de datos
@Data                       // Genera automáticamente getters, setters, toString, equals, y hashCode
@NoArgsConstructor          // Constructor sin argumentos
@AllArgsConstructor         // Constructor con todos los argumentos
public class ResumenEntity {
    @Id
    @NotNull                                 // Anotación de validación personalizada

    private String rut;                 // Identificación única del resumen (rut del estudiante)
    private String nombre_estudiante;   // Nombre del estudiante asociado al resumen
    private int examen_rendido;         // Número de exámenes rendidos
    private double promedio_puntaje;    // Promedio de puntajes obtenidos
    private double monto_totalA;        // Monto total acumulado (podría necesitar mayor explicación)
    private String tipo_pagoCC;         // Tipo de pago con tarjeta de crédito
    private int n_total_cuotas_pactadas;// Número total de cuotas pactadas
    private int n_cuotas_pagadas;       // Número de cuotas pagadas
    private double monto_totalP;        // Monto total de pagos realizados
    private String fecha_pago;          // Fecha del último pago (considera usar un tipo de dato de fecha)
    private double saldo_pagar;         // Saldo pendiente por pagar
    private int n_cuotas_retraso;       // Número de cuotas en retraso
}
