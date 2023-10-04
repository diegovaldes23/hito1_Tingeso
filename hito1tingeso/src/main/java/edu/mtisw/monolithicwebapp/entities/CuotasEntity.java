package edu.mtisw.monolithicwebapp.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "cuotas")         // Nombre de la tabla en la base de datos
@Data                           // Genera automáticamente getters, setters, toString, equals, y hashCode
@NoArgsConstructor              // Constructor sin argumentos
@AllArgsConstructor             // Constructor con todos los argumentos
public class CuotasEntity {
    @Id
    @NotNull // Anotación de validación personalizada
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de claves primarias

    private int id;                 // Identificador único de la cuota
    private String rut;             // Identificación única asociada a la cuota
    private int cantidad_cuotas;    // Cantidad de cuotas asociadas
    private double capital;         // Monto de capital de la cuota
    private double descuento_prueba;// Descuento aplicado (podría necesitar mayor explicación)
    private double multa;           // Monto de la multa (podría necesitar mayor explicación)
    private double monto_total;     // Monto total de la cuota
    private String estado;          // Estado actual de la cuota
    private String fecha_vencimiento;// Fecha de vencimiento de la cuota (considera usar un tipo de dato de fecha)
    private String fecha_pago;
}
