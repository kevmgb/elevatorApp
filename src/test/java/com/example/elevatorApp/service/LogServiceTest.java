package com.example.elevatorApp.service;

import com.example.elevatorApp.entity.Log;
import com.example.elevatorApp.repository.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LogServiceTest {
    @Mock
    private LogRepository logRepository;

    private LogService logService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logService = new LogService(logRepository);
    }

    @Test
    @DisplayName("Test log elevator call. Should save call with correct message and id")
    void testLogElevatorCalledShouldSaveLogWithCorrectMessageAndElevatorId() {
        // Given
        Long elevatorId = 1L;
        int fromFloor = 1;
        int toFloor = 5;
        when(logRepository.save(any(Log.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); //return saved object

        // When
        logService.logElevatorCalled(elevatorId, fromFloor, toFloor);

        // Assert
        verify(logRepository, times(1))
                .save(argThat(log -> log.getEvent().contains("called from floor") && log.getElevatorId().equals(elevatorId)));
    }

    @Test
    @DisplayName("Test get all logs.")
    void testGetAllLogs() {
        // Given
        List<Log> expectedLogs = new ArrayList<>();
        expectedLogs.add(new Log());
        expectedLogs.add(new Log());
        when(logRepository.findAll()).thenReturn(expectedLogs);

        // When
        Iterable<Log> logs = logService.getAllLogs();

        // Assert
        verify(logRepository).findAll();
        assertIterableEquals(expectedLogs, logs);
    }

}