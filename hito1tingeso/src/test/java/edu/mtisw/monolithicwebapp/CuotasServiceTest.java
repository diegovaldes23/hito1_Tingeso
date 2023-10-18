package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.repositories.CuotasRepository;
import edu.mtisw.monolithicwebapp.services.CuotasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CuotasServiceTest {

    @Autowired
    private CuotasService cuotasService;

    @MockBean
    private CuotasRepository cuotasRepository;

    @Test
    void testFindRut() {
        String testRut = "12345678-9";
        CuotasEntity testCuota = new CuotasEntity();
        testCuota.setRut(testRut);

        // Crear una lista de tipo ArrayList y agregar testCuota
        ArrayList<CuotasEntity> testList = new ArrayList<>();
        testList.add(testCuota);

        // Mocking the repository response
        when(cuotasRepository.findRut(testRut)).thenReturn(testList);

        CuotasEntity result = cuotasService.findRut(testRut).get(0);
        assertEquals(testRut, result.getRut());
    }


    @Test
    void testPagando() {
        int testId = 1;
        CuotasEntity testCuota = new CuotasEntity();
        testCuota.setId(testId);
        testCuota.setEstado("Pendiente");

        // Mocking the repository response for findById
        when(cuotasRepository.findById(testId)).thenReturn(testCuota);

        cuotasService.pagando(testId);

        // Asserting that the state has been changed to "Pagado"
        assertEquals("Pagado", testCuota.getEstado());

        // Verifying that the save method was called once
        verify(cuotasRepository, times(1)).save(testCuota);
    }

    @Test
    void testFindCantidadCuotas() {
        String testRut = "12345678-9";
        int expectedCount = 5;

        // Mocking the repository response
        when(cuotasRepository.findCantidadCuotas(testRut)).thenReturn(expectedCount);

        int result = cuotasService.findCantidad(testRut);
        assertEquals(expectedCount, result);
    }


    @Test
    void testFindAll() {
        CuotasEntity testCuota = new CuotasEntity();
        testCuota.setRut("12345678-9");

        // Mocking the repository response
        when(cuotasRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(testCuota)));

        ArrayList<CuotasEntity> result = cuotasService.findAll();
        assertEquals(1, result.size());
        assertEquals("12345678-9", result.get(0).getRut());
    }


    @Test
    void testFindMontoTotalPagado() {
        String testRut = "12345678-9";
        Double expectedMonto = 1000.0;

        // Mocking the repository response
        when(cuotasRepository.findMontoTotalPagado(testRut)).thenReturn(expectedMonto);

        Double result = cuotasService.findMontoTotalPagado(testRut);
        assertEquals(expectedMonto, result);
    }

    @Test
    void testFindMontoTotalPendiente() {
        String testRut = "12345678-9";
        Double expectedMonto = 500.0;

        // Mocking the repository response
        when(cuotasRepository.findMontoTotalPendiente(testRut)).thenReturn(expectedMonto);

        Double result = cuotasService.findMontoTotalPendiente(testRut);
        assertEquals(expectedMonto, result);
    }












    @Test
    void testFindCantidadPagas() {
        String testRut = "12345678-9";
        int expectedCount = 3;

        when(cuotasRepository.findcantidadpagas(testRut)).thenReturn(expectedCount);

        int result = cuotasService.findcantidadpagas(testRut);
        assertEquals(expectedCount, result);
    }

    @Test
    void testPagandoWithinDateRange() {
        int testId = 1;
        CuotasEntity testCuota = new CuotasEntity();
        testCuota.setEstado("Pendiente");

        when(cuotasRepository.findById(testId)).thenReturn(testCuota);

        cuotasService.pagando(testId);

        assertEquals("Pagado", testCuota.getEstado());
        assertNotNull(testCuota.getFecha_pago());
    }





}
