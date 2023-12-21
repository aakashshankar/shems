package com.shems.server.dao;

import com.shems.server.dao.projection.TimeseriesLocationConsumption;
import com.shems.server.dao.projection.LocationAndTotalConsumption;
import com.shems.server.domain.Location;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    String consumptionQuery =
            """
                select
                  l.id,
                  l.address,
                  sum(cast(e.value as decimal)) as total
                from
                  locations l
                  join devices d ON l.id = d.location_id
                  join events e ON e.device_id = d.id
                where
                  e.type = 'energy use'
                  and l.user_id = :customerId
                  and e.timestamp <= :to
                  and e.timestamp >= :from
                group by
                  l.id,
                  l.address
                order BY
                  total desc
            """;

    String dailyConsumptionForALocationQuery =
            """
                select
                  DATE(e.timestamp) as timeunit,
                  l.address,
                  sum(cast(e.value as decimal)) as total
                from
                  locations l
                  join devices d ON l.id = d.location_id
                  join events e ON e.device_id = d.id
                where
                  e.type = 'energy use'
                  and l.user_id = :customerId
                  and l.id = :locationId
                  and e.timestamp <= :to
                  and e.timestamp >= :from
                group by
                  l.id, l.address, timeunit
            """;

    String dailyConsumptionForAllLocations =
            """
                select
                  DATE(e.timestamp) as timeunit,
                  l.address,
                  sum(cast(e.value as decimal)) as total
                from
                  locations l
                  join devices d ON l.id = d.location_id
                  join events e ON e.device_id = d.id
                where
                  e.type = 'energy use'
                  and l.user_id = :customerId
                  and e.timestamp <= :to
                  and e.timestamp >= :from
                group by
                  l.id, l.address, timeunit
            """;
    String weeklyConsumptionForAllLocations =
            """
                select
                  DATE_TRUNC('week', e.timestamp) as timeunit,
                  l.address,
                  sum(cast(e.value as decimal)) as total
                from
                  locations l
                  join devices d ON l.id = d.location_id
                  join events e ON e.device_id = d.id
                where
                  e.type = 'energy use'
                  and l.user_id = :customerId
                  and e.timestamp <= :to
                  and e.timestamp >= :from
                group by
                  l.id, l.address, timeunit
            """;


    String hourlyConsumptionQuery =
            """
                select
                  extract(hour from e.timestamp) as timeunit,
                  l.address,
                  sum(cast(e.value as decimal)) as total
                from
                  locations l
                  join devices d ON l.id = d.location_id
                  join events e ON e.device_id = d.id
                where
                  e.type = 'energy use'
                  and l.user_id = :customerId
                  and l.id = :locationId
                  and e.timestamp <= :to
                  and e.timestamp >= :from
                group by
                  l.id, l.address,timeunit
            """;

    String hourlyConsumptionForAllLocations =
            """
                select
                  date_part('hour', e.timestamp) as timeunit,
                  l.address,
                  sum(cast(e.value as decimal)) as total
                from
                  locations l
                  join devices d ON l.id = d.location_id
                  join events e ON e.device_id = d.id
                where
                  e.type = 'energy use'
                  and l.user_id = :customerId
                  and e.timestamp <= :to
                  and e.timestamp >= :from
                group by
                  l.id, l.address, timeunit
            """;

    String totalConsumptionQuery =
            """
                select
                  sum(cast(e.value as decimal)) as total
                from
                  locations l
                  join devices d ON l.id = d.location_id
                  join events e ON e.device_id = d.id
                where
                  e.type = 'energy use'
                  and l.user_id = :customerId
                  and e.timestamp <= :to
                  and e.timestamp >= :from
            """;

    String avgConsumptionQuery =
            """
                select
                  avg(cast(e.value as decimal)) as total
                from
                  locations l
                  join devices d ON l.id = d.location_id
                  join events e ON e.device_id = d.id
                where
                  e.type = 'energy use'
                  and l.user_id = :customerId
                  and e.timestamp <= :to
                  and e.timestamp >= :from
            """;

    @Query(value = "SELECT * FROM locations l WHERE l.id = :id", nativeQuery = true)
    @Nonnull
    Optional<Location> findById(@Param("id") @Nonnull Long id);

    @Query(value = "SELECT * FROM locations l WHERE l.id IN (:locationIds)", nativeQuery = true)
    Collection<Location> findAllById(@Param("locationIds") Collection<Long> locationIds);

    @Query(value = "SELECT * FROM locations l WHERE l.user_id = :customerId",
            nativeQuery = true)
    Collection<Location> findAllByUserId(@Param("customerId") Long customerId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM locations l WHERE l.id IN (:locationIds)")
    void deleteByIds(@Param("locationIds") Collection<Long> locationIds);

    @Query(value = consumptionQuery,
            nativeQuery = true)
    Collection<LocationAndTotalConsumption> findConsumption(@Param("customerId") Long customerId,
                                                            @Param("from") Date from, @Param("to") Date to);

    @Query(nativeQuery = true, value = dailyConsumptionForAllLocations)
    Collection<TimeseriesLocationConsumption> findDailyConsumptionForAllLocations(@Param("customerId") Long customerId,
                                                                            @Param("from") Date timestamp, @Param("to") Date to);

    @Query(nativeQuery = true, value = totalConsumptionQuery)
    Double findTotalConsumption(@Param("customerId") Long customerId,
                                                     @Param("from") Date timestamp, @Param("to") Date to);

    @Query(nativeQuery = true, value = avgConsumptionQuery)
    Double findAvgConsumption(@Param("customerId") Long customerId,
                              @Param("from") Date timestamp, @Param("to") Date to);

    @Query(nativeQuery = true, value = dailyConsumptionForALocationQuery)
    Collection<TimeseriesLocationConsumption> findDailyConsumptionForALocation(@Param("customerId") Long customerId,
                                                                   @Param("locationId") Long locationId,
                                                                   @Param("from") Date from, @Param("to") Date to);


    @Query(nativeQuery = true, value = hourlyConsumptionQuery)
    Collection<TimeseriesLocationConsumption> findHourlyConsumptionForALocation(@Param("customerId") Long customerId,
                                                                          @Param("locationId") Long locationId,
                                                                          @Param("from") Date from, @Param("to") Date to);

    @Query(nativeQuery = true, value = hourlyConsumptionForAllLocations)
    Collection<TimeseriesLocationConsumption> findHourlyConsumptionForAllLocations(@Param("customerId") Long customerId,
                                                                             @Param("from") Date from, @Param("to") Date to);

    @Query(nativeQuery = true, value = weeklyConsumptionForAllLocations)
    Collection<TimeseriesLocationConsumption> findWeeklyConsumptionForAllLocations(@Param("customerId") Long customerId,
                                                                             @Param("from") Date from, @Param("to") Date to);
}
