package com.antifurto.antifurto_service.repository;

import com.antifurto.antifurto_service.model.SensorEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorEventRepository extends JpaRepository<SensorEvent, Long> {
}
