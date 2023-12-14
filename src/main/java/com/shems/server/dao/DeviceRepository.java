package com.shems.server.dao;

import com.shems.server.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    String baseQuery = "SELECT d.id, d.modelnumber, d.type, d.location_id, d.enrollmentdate FROM devices d";

    @Query(value = baseQuery + " JOIN location l ON d.location_id = l.id " +
            "JOIN customers c ON c.id = l.customer_id " +
            "WHERE c.id = :customerId", nativeQuery = true)
    List<Device> findAllByUserId(@Param("customerId") Long customerId);

    @Query("SELECT d FROM Device d WHERE d.location IS NULL AND d.location.user.id = :customerId")
    List<Device> findAllUnregistered(@Param("customerId") Long customer);
}
