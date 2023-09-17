package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import org.springframework.stereotype.Service;

@Service
public class Pago {
    //Maximo de Cuotas por tipo de colegio

    public int maximoCuotasColegio(EstudiantesEntity estudiante){
        int maximo = 0;

        // Usamos equals para comparar el contenido de las cadenas
        if(estudiante.getTipo_colegio().equals("Municipal")){
            maximo = 10;
        }
        if(estudiante.getTipo_colegio().equals("Subvencionado")){
            maximo = 7;
        }
        if(estudiante.getTipo_colegio().equals("Privado")){
            maximo = 4;
        }

        return maximo; // Deberías devolver el valor maximo
    }

    public double descuentoentrada(EstudiantesEntity estudiante,String tipopago){
        double matricula=70000;
        double arancel=1500000;
        double nuevopago=0;

        if (tipopago.equals("cuotas")){
            // Usamos equals para comparar el contenido de las cadenas y colocar descuentos
            if(estudiante.getTipo_colegio().equals("Municipal")){
                nuevopago = arancel - arancel*0.2;
            }
            if(estudiante.getTipo_colegio().equals("Subvencionado")){
                nuevopago = arancel - arancel*0.1;
            }
            if(estudiante.getTipo_colegio().equals("Privado")){
                nuevopago = arancel;
            }
        }
        if (tipopago.equals("contado")) {
            nuevopago = arancel - arancel * 0.5;
        }

        return nuevopago;



    }





}

