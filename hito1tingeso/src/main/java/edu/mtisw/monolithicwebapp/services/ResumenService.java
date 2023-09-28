package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.EstudiantesEntity;
import edu.mtisw.monolithicwebapp.entities.ResumenEntity;
import edu.mtisw.monolithicwebapp.repositories.EstudiantesRepository;
import edu.mtisw.monolithicwebapp.repositories.ResumenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ResumenService {
    @Autowired
    ResumenRepository resumenRepository;

    public ArrayList<ResumenEntity> obtenerResumenes(){
        return (ArrayList<ResumenEntity>) resumenRepository.findAll();
    }



}
