package com.shems.server.dao;

import com.shems.server.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(value = "SELECT d " +
            "FROM devices d JOIN locations l ON d.location_id = l.id " +
            "JOIN customers c ON c.id = l.user_id " +
            "WHERE c.id = :customerId", nativeQuery = true)
    List<Device> findAllByUserId(@Param("customerId") Long customerId);

    @Query(value = "SELECT d " +
            "FROM devices d JOIN locations l ON d.location_id = l.id " +
            "JOIN customers c ON l.user_id = c.id " +
            "WHERE d.location_id IS NULL AND c.id = :customerId", nativeQuery = true)
    List<Device> findAllUnregistered(@Param("customerId") Long customer);
}
