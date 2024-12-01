package com.uber.UberApp.repositories;

import com.uber.UberApp.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
}
