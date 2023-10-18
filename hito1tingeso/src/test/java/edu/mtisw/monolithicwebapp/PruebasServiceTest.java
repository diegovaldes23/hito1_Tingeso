package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.repositories.PruebasRepository;
import edu.mtisw.monolithicwebapp.services.PruebasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PruebasServiceTest {

    @InjectMocks
    PruebasService pruebasService;

    @Mock
    PruebasRepository pruebasRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerPruebas() {
        List<PruebasEntity> pruebasEntities = new ArrayList<>();
        pruebasEntities.add(new PruebasEntity(1, "20.847.708-0", "01/09/2021", "750"));
        when(pruebasRepository.findAll()).thenReturn(pruebasEntities);

        List<PruebasEntity> result = pruebasService.obtenerPruebas();

        assertEquals(1, result.size());
        assertEquals("20.847.708-0", result.get(0).getRut_estudiante());
    }

    @Test
    public void testGuardarArchivo() {
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        String result = pruebasService.guardar(file);

        assertEquals("Archivo guardado con exito!", result);
    }

    @Test
    public void testFindPromedioPuntaje() {
        when(pruebasRepository.findPromedioPuntaje("20.847.708-0")).thenReturn(750.0);
        Double result = pruebasService.findPromedioPuntaje("20.847.708-0");
        assertEquals(750.0, result);
    }


    @Test
    public void testLeerTxt() {
        // Considerando que leerTxt no devuelve un valor, este método solo verificará que no lance una excepción.
        // Asumiremos que el archivo "test.txt" contiene tres registros válidos.
        pruebasService.leerTxt("test.txt");
    }

    @Test
    public void testGuardarPrueba() {
        PruebasEntity prueba = new PruebasEntity(1, "20.847.708-0", "01/09/2021", "750");
        pruebasService.guardarPrueba(prueba);
        verify(pruebasRepository, times(1)).save(prueba);
    }

    @Test
    public void testGuardarPruebasDB() {
        pruebasService.guardarPruebasDB("20.847.708-0", "01/09/2021", "750");
        // Como este método internamente llama a guardarPrueba, verificamos que dicho método se haya llamado.
        verify(pruebasRepository, times(1)).save(any(PruebasEntity.class));
    }

    @Test
    public void testEliminarPrueba() {
        ArrayList<PruebasEntity> pruebas = new ArrayList<>();
        pruebas.add(new PruebasEntity(1, "20.847.708-0", "01/09/2021", "750"));
        pruebasService.eliminarPrueba(pruebas);
        verify(pruebasRepository, times(1)).deleteAll(pruebas);
    }

    @Test
    public void testFindByRut() {
        ArrayList<PruebasEntity> pruebas = new ArrayList<>();
        pruebas.add(new PruebasEntity(1, "20.847.708-0", "01/09/2021", "750"));
        when(pruebasRepository.findByRut("20.847.708-0")).thenReturn(pruebas);

        ArrayList<PruebasEntity> result = pruebasService.findByRut("20.847.708-0");
        assertEquals(1, result.size());
    }

}
