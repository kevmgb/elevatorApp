package com.example.elevatorApp.service;

import com.example.elevatorApp.entity.Log;
import com.example.elevatorApp.enums.ElevatorDirection;
import com.example.elevatorApp.enums.ElevatorState;
import com.example.elevatorApp.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public void logElevatorCalled(Long elevatorId, int fromFloor, int toFloor) {
        String event = String.format("Elevator with ID %d called from floor %d to floor %d", elevatorId, fromFloor, toFloor);
        saveLog(event, elevatorId);
    }

    public void logElevatorArrived(Long elevatorId, int floor) {
        String event = String.format("Elevator with ID %d arrived at floor %d", elevatorId, floor);
        saveLog(event, elevatorId);
    }

    public void logElevatorStateChanged(Long elevatorId, ElevatorState state, ElevatorDirection direction, int currentFloor) {
        String event = String.format("Elevator with ID %d changed state to %s and direction to %s. It is currently at floor %s", elevatorId, state, direction, currentFloor);
        saveLog(event, elevatorId);
    }

    private void saveLog(String event, Long elevatorId) {
        Log log = new Log();
        log.setTimestamp(LocalDateTime.now());
        log.setEvent(event);
        log.setElevatorId(elevatorId);
        logRepository.save(log);
    }

    public Iterable<Log> getAllLogs() {
        return logRepository.findAll();
    }
}