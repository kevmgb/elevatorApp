package com.example.elevatorApp.controller;

import com.example.elevatorApp.entity.Log;
import com.example.elevatorApp.model.pojo.ElevatorStatus;
import com.example.elevatorApp.model.response.Response;
import com.example.elevatorApp.service.ElevatorService;
import com.example.elevatorApp.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/elevator")
@RequiredArgsConstructor
public class ElevatorController {
    private final ElevatorService elevatorService;
    private final LogService logService;

    @PostMapping("/call")
    public ResponseEntity<Response> callElevator(@RequestParam int fromFloor, @RequestParam int toFloor) {
        return elevatorService.callElevator(fromFloor, toFloor);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<ElevatorStatus>> getElevatorStatus() {
        return elevatorService.getElevatorStatus();
    }

    @GetMapping("/logs")
    public ResponseEntity<Iterable<Log>> getLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }
}
