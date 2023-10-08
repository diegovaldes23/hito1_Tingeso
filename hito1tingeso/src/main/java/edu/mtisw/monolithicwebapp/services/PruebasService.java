package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.PruebasEntity;
import edu.mtisw.monolithicwebapp.repositories.PruebasRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
@Service
public class PruebasService {
    @Autowired
    PruebasRepository pruebasRepository;

    private final Logger logg = LoggerFactory.getLogger(PruebasService.class);

    //Obtener todos las pruebas
    public ArrayList<PruebasEntity> obtenerPruebas(){ return (ArrayList<PruebasEntity>) pruebasRepository.findAll();}

    public Double findPromedioPuntaje(String rut){return pruebasRepository.findPromedioPuntaje(rut);};

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }


    @Generated
    public void leerTxt(String direccion){
        String texto = "";
        BufferedReader bf = null;
        pruebasRepository.deleteAll();

        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            while((bfRead = bf.readLine()) != null){
                guardarPruebasDB(bfRead.split(",")[0], bfRead.split(",")[1], bfRead.split(",")[2]);
                temp = temp + "\n" + bfRead;
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public void guardarPrueba(PruebasEntity prueba){
        pruebasRepository.save(prueba);
    }


    public void guardarPruebasDB(String rut, String fecha_examen, String puntaje){
        PruebasEntity newPrueba = new PruebasEntity();
        newPrueba.setRut_estudiante(rut);
        newPrueba.setFecha_examen(fecha_examen);
        newPrueba.setPuntaje(puntaje);
        guardarPrueba(newPrueba);
    }

    public void eliminarPrueba(ArrayList<PruebasEntity> pruebas){
        pruebasRepository.deleteAll(pruebas);
    }


    public ArrayList<PruebasEntity> findByRut(String rut) {  return  (ArrayList<PruebasEntity>) pruebasRepository.findByRut(rut);
    }


}