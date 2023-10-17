package com.example.apirestsoccerplayers.controllers.positions;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.apirestsoccerplayers.controllers.country.Country;
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

    @GetMapping("/auth/{name}")
    public Result getPosition(@PathVariable(name="name") String positionName)throws NameNotFoundException{
        Position position = positionService.getPosition(positionName);
        return new Result(true, 200, "Success", position);
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

    //BULK DATA
    @PostMapping(path="/auth/add/many-data")
    public Result addManyPositions(@RequestParam("file") MultipartFile file) throws IOException{
        List<Position> positions = positionService.addManyPositions(file);
        return new Result(true, 201, "file saved and positions added", positions);
    }
}
