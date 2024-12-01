package com.uber.UberApp.repositories;


import com.uber.UberApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
