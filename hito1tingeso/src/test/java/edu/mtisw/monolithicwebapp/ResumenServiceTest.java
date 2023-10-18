package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.entities.ResumenEntity;
import edu.mtisw.monolithicwebapp.repositories.ResumenRepository;
import edu.mtisw.monolithicwebapp.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResumenServiceTest {

    @InjectMocks
    ResumenService resumenService;

    @Mock
    ResumenRepository resumenRepository;

    @Mock
    CuotasService cuotasService;

    @Mock
    EstudiantesService estudiantesService;

    @Mock
    Pago pagoService;

    @Mock
    PruebasService pruebasService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerResumenes() {
        ArrayList<ResumenEntity> resumenEntities = new ArrayList<>();
        resumenEntities.add(new ResumenEntity("20.847.708-0", "Juan", 2, 750.0, 1500.0, "Cuotas", 12, 6, 750.0, "01/09/2021", 750.0, 1));
        when(resumenRepository.findAll()).thenReturn(resumenEntities);

        ArrayList<ResumenEntity> result = resumenService.obtenerResumenes();

        assertEquals(1, result.size());
        assertEquals("20.847.708-0", result.get(0).getRut());
    }

    @Test
    public void testCalculoResumen() throws ParseException {
        EstudiantesEntity estudiante = new EstudiantesEntity();
        estudiante.setRut("20.847.708-0");
        estudiante.setNombres("Juan");
        when(estudiantesService.findByRut(anyString())).thenReturn(estudiante);

        ArrayList<CuotasEntity> cuotas = new ArrayList<>();
        CuotasEntity cuota = new CuotasEntity();
        cuota.setFecha_pago("01/09/2021");
        cuota.setEstado("Pagado");
        cuotas.add(cuota);
        when(cuotasService.findRut(anyString())).thenReturn(cuotas);

        resumenService.calculoResumen("20.847.708-0");

        // Puedes agregar más aserciones para verificar otros aspectos del resumen
    }

    @Test
    public void testCalcularUltimoPago() {
        ArrayList<CuotasEntity> cuotas = new ArrayList<>();
        CuotasEntity cuota = new CuotasEntity();
        cuota.setFecha_pago("01/09/2021");
        cuota.setEstado("Pagado");
        cuotas.add(cuota);

        String fecha = resumenService.calcularUltimoPago(cuotas);

        assertEquals("01/09/2021", fecha);
    }

    @Test
    public void testVerTipo() {
        assertEquals("Contado", resumenService.verTipo(0));
        assertEquals("Cuotas", resumenService.verTipo(5));
    }

    // ... más pruebas para otros métodos ...

}
