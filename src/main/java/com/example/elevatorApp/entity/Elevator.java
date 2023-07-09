package com.example.elevatorApp.entity;

import com.example.elevatorApp.enums.ElevatorDirection;
import com.example.elevatorApp.enums.ElevatorState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_elevator")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int currentFloor;
    private ElevatorState state;
    private ElevatorDirection direction;

    public Elevator(int currentFloor, ElevatorState state, ElevatorDirection direction) {
        this.currentFloor = currentFloor;
        this.state = state;
        this.direction = direction;
    }
}
