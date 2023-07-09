package com.example.elevatorApp.service;

import com.example.elevatorApp.entity.Elevator;
import com.example.elevatorApp.enums.ElevatorDirection;
import com.example.elevatorApp.enums.ElevatorState;
import com.example.elevatorApp.model.response.Response;
import com.example.elevatorApp.repository.ElevatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElevatorService {
    private final int numFloors = 10; // Configurable number of floors
    private final int floorTravelTime = 1000; // Time in milliseconds for elevator to travel 1 floor (5 seconds)
    private final int doorOperationTime = 100; // Time for door to open in milliseconds
    private final ElevatorRepository elevatorRepository;

    public ResponseEntity<Response> callElevator(int fromFloor, int toFloor) {
        if (fromFloor < 0 || toFloor > numFloors) {
            return ResponseEntity.badRequest().body(new Response("Requested floor is out of bounds!"));
        }

        // Find an available elevator
        Elevator elevator = findAvailableElevator(fromFloor);

        // If no elevator is available, return message
        if (elevator == null) {
            return ResponseEntity.badRequest().body(new Response("All elevators are busy at the moment. Please wait and try again later."));
        }

        // Update the elevator's state and direction
        elevator.setState(ElevatorState.MOVING);
        elevator.setDirection(calculateDirection(elevator.getCurrentFloor(), fromFloor));
        elevatorRepository.save(elevator);

        // Start moving the elevator asynchronously
        moveElevatorAsync(elevator, fromFloor);


        ElevatorDirection direction2 = calculateDirection(elevator.getCurrentFloor(), toFloor);
        elevator.setState(ElevatorState.MOVING);
        elevator.setDirection(direction2);
        elevatorRepository.save(elevator);

        // Once it reaches the floor it was called from, begin moving to floor called to
        moveElevatorAsync(elevator, toFloor);
        elevator.setState(ElevatorState.IDLE);
        elevator.setDirection(ElevatorDirection.IDLE);
        elevatorRepository.save(elevator);

        return ResponseEntity.ok().body(new Response("Elevator called successfully"));
    }

    private void moveElevatorAsync(Elevator elevator, int targetFloor) {
        // Update the elevator's current floor until it reaches the destination floor
        while (elevator.getCurrentFloor() != targetFloor) {
            // Move the elevator by one floor based on the direction
            if (elevator.getDirection() == ElevatorDirection.UP) {
                if (elevator.getCurrentFloor() < 10) {
                    elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
                    elevatorRepository.save(elevator);

                }
            } else if (elevator.getDirection() == ElevatorDirection.DOWN) {
                if (elevator.getCurrentFloor() > 1) {
                    elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
                    elevatorRepository.save(elevator);
                }

            }

            // Sleep for a specific duration to simulate the time taken to move one floor
            try {
                Thread.sleep(floorTravelTime); // Sleep for 5 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Update the elevator's state and direction after reaching the destination
        elevator.setState(ElevatorState.STOPPED);
        elevator.setDirection(ElevatorDirection.IDLE);
        elevatorRepository.save(elevator);

        // Simulate opening of doors
        try {
            Thread.sleep(doorOperationTime); // Sleep for 5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    private Elevator findAvailableElevator(int fromFloor) {
        List<Elevator> elevators = elevatorRepository.findAll();

        for (Elevator elevator : elevators) {
            if (elevator.getState() == ElevatorState.IDLE) {
                if (isElevatorReachable(elevator, fromFloor)) {
                    return elevator;
                }
            }
        }
        return null;
    }

    private ElevatorDirection calculateDirection(int fromFloor, int toFloor) {
        // Determine which direction the user wants to move
        if (fromFloor < toFloor) {
            return ElevatorDirection.UP;
        } else if (fromFloor > toFloor) {
            return ElevatorDirection.DOWN;
        } else {
            return ElevatorDirection.IDLE;
        }
    }

    private boolean isElevatorReachable(Elevator elevator, int floor) {
        // If the elevator is moving up and the calling floor is above or at the same level
        // If the elevator is moving down and the calling floor is below or at the same level
        // If the elevator is idle, it can reach any floor

        if (elevator.getDirection() == ElevatorDirection.UP && floor >= elevator.getCurrentFloor()) {
            return true;
        } else if (elevator.getDirection() == ElevatorDirection.DOWN && floor <= elevator.getCurrentFloor()) {
            return true;
        } else {
            return elevator.getDirection() == ElevatorDirection.IDLE;
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeElevators() {
        // Initialize 2 elevators, all initially at ground floor
        Elevator elevator1 = new Elevator(1, ElevatorState.IDLE, ElevatorDirection.IDLE);
        Elevator elevator2 = new Elevator(1, ElevatorState.IDLE, ElevatorDirection.IDLE);
        elevatorRepository.saveAll(List.of(elevator1, elevator2));
    }
}
