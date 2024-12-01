package com.uber.UberApp.repositories;

import com.uber.UberApp.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride,Long> {
}
