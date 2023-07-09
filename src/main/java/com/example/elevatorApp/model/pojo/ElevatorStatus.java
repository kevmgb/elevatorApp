package com.example.elevatorApp.model.pojo;

import com.example.elevatorApp.enums.ElevatorDirection;
import com.example.elevatorApp.enums.ElevatorState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElevatorStatus {
    private int currentFloor;
    private ElevatorState state;
    private ElevatorDirection direction;
}