package com.example.apirestsoccerplayers.controllers.positions;

import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    public Position addPosition(PositionDTO request) throws DataIntegrityViolationException{
        Position position = Position.builder()
            .name(request.getName())
            .build();
        
        return positionRepository.save(position);
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
}
