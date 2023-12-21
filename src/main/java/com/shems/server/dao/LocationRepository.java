package com.shems.server.dao;

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
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    String topConsumptionQuery =
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
                group BY
                  l.id,
                  l.address
                order BY
                  total desc
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

    @Query(value = topConsumptionQuery,
            nativeQuery = true)
    Collection<LocationAndTotalConsumption> findTopConsumption(@Param("customerId") Long customerId);
}
