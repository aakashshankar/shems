package com.shems.server.dao;

import com.shems.server.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(value = "SELECT d " +
            "FROM devices d JOIN locations l ON d.location_id = l.id " +
            "JOIN customers c ON c.id = l.user_id " +
            "WHERE c.id = :customerId", nativeQuery = true)
    List<Device> findAllByUserId(Long customerId);
}
