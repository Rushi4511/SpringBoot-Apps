package com.uber.UberApp.repositories;

import com.uber.UberApp.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository  extends JpaRepository<RideRequest,Long> {
}
