package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import org.springframework.stereotype.Service;

@Service
public class Pago {
    //Maximo de Cuotas por tipo de colegio

    public boolean maximoCuotasColegio(EstudiantesEntity estudiante,int cuota) {
        int maximo = 0;

        // Usamos equals para comparar el contenido de las cadenas
        if (estudiante.getTipo_colegio().equals("Municipal")) {
            maximo = 10;
        }
        if (estudiante.getTipo_colegio().equals("Subvencionado")) {
            maximo = 7;
        }
        if (estudiante.getTipo_colegio().equals("Privado")) {
            maximo = 4;
        }

        // Verificar si la cantidad de cuotas está dentro del rango permitido

        return cuota >= 0 && cuota <= maximo;

    }


    public double calcularDescuentoEntrada(EstudiantesEntity estudiante, String tipoPago) {
        double arancel = 1500000;
        double descuentoTipoColegio = 0.0;
        double descuentoAnos = 0.0;

        // Convertir el tipo de colegio ingresado a minúsculas para hacerlo insensible a mayúsculas/minúsculas
        String tipoColegioIngresado = estudiante.getTipo_colegio().toLowerCase();

        // Convertir el tipo de pago ingresado a minúsculas para hacerlo insensible a mayúsculas/minúsculas
        String tipoPagoIngresado = tipoPago.toLowerCase();

        // Calcular descuento según Tipo de colegio de procedencia
        if (tipoPagoIngresado.equals("cuotas")) {
            switch (tipoColegioIngresado) {
                case "municipal":
                    descuentoTipoColegio = 0.2; // 20% de descuento para colegio municipal
                    break;
                case "subvencionado":
                    descuentoTipoColegio = 0.1; // 10% de descuento para colegio subvencionado
                    break;
                case "privado":
                    descuentoTipoColegio = 0.0; // Sin descuento para colegio privado
                    break;
            }
        } else if (tipoPagoIngresado.equals("contado")) {
            descuentoTipoColegio = 0.5; // 50% de descuento para pago al contado
        }

        // Calcular descuento según años desde que egresó del colegio
        int cantidadAnos = 2023 - estudiante.getAno_egreso();
        if (cantidadAnos < 1) {
            descuentoAnos = 0.15; // 15% de descuento para menos de 1 año
        } else if (cantidadAnos >= 1 && cantidadAnos <= 2) {
            descuentoAnos = 0.08; // 8% de descuento para 1-2 años
        } else if (cantidadAnos >= 3 && cantidadAnos <= 4) {
            descuentoAnos = 0.04; // 4% de descuento para 3-4 años
        }

        // Calcular el nuevo arancel con los descuentos aplicados
        double nuevoArancel = arancel - (arancel * descuentoTipoColegio) - (arancel * descuentoAnos);

        return nuevoArancel;
    }


    public double calcularDescuentoPuntajes(PruebasEntity prueba,String tipoPago, int arancelmes) {

        double descuentopuntaje = 0.0;
        double pagafinal=0.0;

        // Convertir el tipo de pago ingresado a minúsculas para hacerlo insensible a mayúsculas/minúsculas
        String tipoPagoIngresado = tipoPago.toLowerCase();

        // Calcular descuento según el puntaje
        int puntajeprom = prueba.getPuntaje();
        if (tipoPagoIngresado.equals("cuotas")) {

            if (puntajeprom < 850) {
                descuentopuntaje = 0.0;
            } else if (puntajeprom <= 899) {
                descuentopuntaje = 0.02;
            } else if (puntajeprom <= 949) {
                descuentopuntaje = 0.05;
            } else if (puntajeprom <= 1000) {
                descuentopuntaje = 0.1;
            }
        }else {
            //ESTA POR VERSE LA PARTE DEL CONTADO
        }

        pagafinal = arancelmes - (arancelmes*descuentopuntaje);
        return pagafinal;



    }




}
