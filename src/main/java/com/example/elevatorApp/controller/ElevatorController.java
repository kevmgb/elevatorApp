package com.example.elevatorApp.controller;

import com.example.elevatorApp.model.response.Response;
import com.example.elevatorApp.service.ElevatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/elevator")
@RequiredArgsConstructor
public class ElevatorController {
    private final ElevatorService elevatorService;

    @PostMapping("/call")
    public ResponseEntity<Response> callElevator(@RequestParam int fromFloor, @RequestParam int toFloor) {
        return elevatorService.callElevator(fromFloor, toFloor);
    }
}
