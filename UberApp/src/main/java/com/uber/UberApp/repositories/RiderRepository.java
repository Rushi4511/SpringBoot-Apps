package com.uber.UberApp.repositories;


import com.uber.UberApp.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider,Long> {
}
