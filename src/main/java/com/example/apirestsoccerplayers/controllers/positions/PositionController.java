package com.example.apirestsoccerplayers.controllers.positions;

import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apirestsoccerplayers.handlers.Result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/positions")
public class PositionController {
    private final PositionService positionService;

    @PostMapping("/add")
    public Result addPosition(@Validated @RequestBody PositionDTO request){
        Position position = positionService.addPosition(request);
        return new Result(true, 201, "Position added", position);
    }

    @GetMapping("/auth")
    public Result getAllPositions(){
        List<Position> positions = positionService.getAllPositions();
        return new Result(true, 200, "Success", positions);
    }

    @PutMapping("/auth/update/{id}")
    public Result updatePlayer(@PathVariable(name="id") Integer positionId, @Valid @RequestBody PositionDTO request) throws NameNotFoundException, NotFoundException{
        Position positionUpdated = positionService.updatePosition(positionId, request);
        return new Result(true, 201, "Position updated", positionUpdated);
    }

    @DeleteMapping("/auth/delete/{id}")
    public String deletePosition(@PathVariable(name="id") Integer positionId){
        return positionService.deletePosition(positionId);
    }
}
