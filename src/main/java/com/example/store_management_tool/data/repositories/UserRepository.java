package com.example.store_management_tool.data.repositories;

import com.example.store_management_tool.data.entities.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<StoreUser, Integer> {
    public Optional<StoreUser> findByUsername(String username);
}
