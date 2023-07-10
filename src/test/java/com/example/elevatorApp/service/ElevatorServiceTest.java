package com.example.elevatorApp.service;

import com.example.elevatorApp.entity.Elevator;
import com.example.elevatorApp.enums.ElevatorDirection;
import com.example.elevatorApp.enums.ElevatorState;
import com.example.elevatorApp.model.pojo.ElevatorStatus;
import com.example.elevatorApp.model.response.Response;
import com.example.elevatorApp.repository.ElevatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ElevatorServiceTest {
    @Mock
    private ElevatorRepository elevatorRepository;
    @Mock
    private LogService logService;

    private ElevatorService elevatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        elevatorService = new ElevatorService(elevatorRepository, logService);
    }

    @Test
    @DisplayName("Given valid request, elevator call should return success response")
    void testCallElevatorValidRequest() {
        // Given
        int fromFloor = 1;
        int toFloor = 5;
        Elevator elevator = new Elevator(1, ElevatorState.IDLE, ElevatorDirection.IDLE);
        when(elevatorRepository.findAll()).thenReturn(List.of(elevator));

        // When
        ResponseEntity<Response> responseEntity = elevatorService.callElevator(fromFloor, toFloor);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Elevator called successfully", responseEntity.getBody().getMessage());
        assertEquals(ElevatorState.IDLE, elevator.getState());
        assertEquals(ElevatorDirection.IDLE, elevator.getDirection());
        assertEquals(toFloor, elevator.getCurrentFloor());
    }

    @Test
    @DisplayName("Given invalid floor, should return bad request")
    void testCallElevatorInvalidFromFloor() {
        // Given
        int fromFloor = -1;
        int toFloor = 5;

        // When
        ResponseEntity<Response> responseEntity = elevatorService.callElevator(fromFloor, toFloor);

        // Assert
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Requested floor is out of bounds!", responseEntity.getBody().getMessage());
    }

    @Test
    @DisplayName("If no available elevator is found, should return bad request")
    void testCallElevatorNoAvailableElevator() {
        // Given
        int fromFloor = 1;
        int toFloor = 5;
        when(elevatorRepository.findAll()).thenReturn(List.of(new Elevator(1, ElevatorState.MOVING, ElevatorDirection.UP)));

        // When
        ResponseEntity<Response> responseEntity = elevatorService.callElevator(fromFloor, toFloor);

        // Assert
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("All elevators are busy at the moment. Please wait and try again later.", responseEntity.getBody().getMessage());
        verify(elevatorRepository).findAll();
    }

    @Test
    @DisplayName("Test get elevator status list")
    void testGetElevatorStatus() {
        // Given
        Elevator elevator1 = new Elevator(1, ElevatorState.MOVING, ElevatorDirection.UP);
        Elevator elevator2 = new Elevator(5, ElevatorState.IDLE, ElevatorDirection.IDLE);
        when(elevatorRepository.findAll()).thenReturn(List.of(elevator1, elevator2));

        // When
        ResponseEntity<List<ElevatorStatus>> responseEntity = elevatorService.getElevatorStatus();

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        List<ElevatorStatus> elevatorStatusList = responseEntity.getBody();
        assertNotNull(elevatorStatusList);
        assertEquals(2, elevatorStatusList.size());
        assertEquals(elevator1.getCurrentFloor(), elevatorStatusList.get(0).getCurrentFloor());
        assertEquals(elevator1.getState(), elevatorStatusList.get(0).getState());
        assertEquals(elevator1.getDirection(), elevatorStatusList.get(0).getDirection());
        assertEquals(elevator2.getCurrentFloor(), elevatorStatusList.get(1).getCurrentFloor());
        assertEquals(elevator2.getState(), elevatorStatusList.get(1).getState());
        assertEquals(elevator2.getDirection(), elevatorStatusList.get(1).getDirection());
        verify(elevatorRepository).findAll();
    }
}