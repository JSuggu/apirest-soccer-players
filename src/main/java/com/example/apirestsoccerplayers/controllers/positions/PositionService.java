package com.example.apirestsoccerplayers.controllers.positions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.apirestsoccerplayers.controllers.country.Country;
import com.example.apirestsoccerplayers.utils.file_storage.StorageService;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final StorageService storageService;

    public Position addPosition(PositionDTO request) throws DataIntegrityViolationException{
        Position position = Position.builder()
            .name(request.getName())
            .build();
        
        return positionRepository.save(position);
    }

    public Position getPosition(String positionName)throws NameNotFoundException{
        Position position = positionRepository.findByName(positionName).orElseThrow(()-> new NameNotFoundException("Position name not found"));
        return position;
    }

    public List<Position> getAllPositions(){
        List<Position> positions = positionRepository.findAll();
        return positions;
    }

    public Position updatePosition(Integer positionId, PositionDTO request) throws DataIntegrityViolationException, NotFoundException, NameNotFoundException{
        Position dbPosition = positionRepository.findById(positionId).orElseThrow(()-> new NotFoundException());
        dbPosition.setName(request.getName());

        return positionRepository.save(dbPosition);
    }

    public String deletePosition(Integer positionId){
        positionRepository.deleteById(positionId);
        return "League deleted";
    }

    //BULK DATA
    public List<Position> addManyPositions(MultipartFile file) throws IOException, DataIntegrityViolationException, ConstraintViolationException{
        storageService.saveFile(file);

        List<Position> positionsOfFile = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        Resource resource = storageService.loadFile(fileName);
        File savedFile = resource.getFile();
        BufferedReader br = new BufferedReader(new FileReader(savedFile, StandardCharsets.UTF_8));
        String positionName;

        while((positionName = br.readLine()) != null){
            positionsOfFile.add(Position.builder()
            .name(positionName)
            .build());
        }
        
        br.close();

        return positionRepository.saveAll(positionsOfFile);
    }
}
