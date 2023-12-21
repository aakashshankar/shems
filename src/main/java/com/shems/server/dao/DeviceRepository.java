package com.shems.server.dao;

import com.shems.server.dao.projection.DeviceAndTotalConsumption;
import com.shems.server.domain.Device;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    String baseQuery =
            """
                select
                  d.id,
                  d.model_number,
                  d.type,
                  d.location_id,
                  d.enrollment_date
                from
                  devices d
                  JOIN locations l on d.location_id = l.id
                  JOIN customers c on c.id = l.user_id
                where c.id = :customerId
            """;

    String consumptionPerDeviceQuery =
            """
    select
                  d.id,
                  d.type,
                  sum(cast(e.value as decimal)) as total
                from
                  devices d
                  join locations l on d.location_id = l.id
                  join events e on d.id = e.device_id
                where
                  e.type = 'energy use'
                  and l.user_id = :customerId
                  and e.timestamp <= :to
                  and e.timestamp >= :from
                group by
                  d.id,
                  d.type
            """;

    String totalConsumptionQuery =
            """
                select
                  sum(cast(e.value as decimal)) as total
                from
                  devices d
                  join locations l on d.location_id = l.id
                  join events e on d.id = e.device_id
                where
                  e.type = 'energy use'
                  and l.user_id = :customerId
                  and e.timestamp <= :to
                  and e.timestamp >= :from
            """;

    @Query(value = baseQuery, nativeQuery = true)
    List<Device> findAllByUserId(@Param("customerId") Long customerId);

    @Query(value = baseQuery + " and d.location_id IS NULL", nativeQuery = true)
    List<Device> findAllUnregistered(@Param("customerId") Long customer);

    @Query(value = "SELECT count(*) FROM devices d", nativeQuery = true)
    long count();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM devices d WHERE d.id = :deviceId")
    void deleteById(@Param("deviceId") @Nonnull Long deviceId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM devices d WHERE d.id IN (:deviceIds)")
    void deleteByIds(@Param("deviceIds") Collection<Long> deviceIds);

    @Query(nativeQuery = true, value = baseQuery + " and l.id = :locationId")
    Collection<Device> findAllByLocationId(@Param("customerId") Long customerId, @Param("locationId") Long locationId);

    @Query(nativeQuery = true, value = consumptionPerDeviceQuery)
    Collection<DeviceAndTotalConsumption> getTopConsumption(@Param("customerId") Long customerId,
                                                            @Param("from") Date timestamp, @Param("to") Date to);
}
