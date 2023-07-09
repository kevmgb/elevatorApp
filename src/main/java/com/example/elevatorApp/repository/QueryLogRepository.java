package com.example.elevatorApp.repository;


import com.example.elevatorApp.entity.QueryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryLogRepository extends JpaRepository<QueryLog, Long> {
}
