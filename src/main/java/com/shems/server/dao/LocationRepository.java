package com.shems.server.dao;

import com.shems.server.domain.Location;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "SELECT * FROM locations l WHERE l.id = :id", nativeQuery = true)
    @Nonnull
    Optional<Location> findById(@Param("id") @Nonnull Long id);

    @Query(value = "SELECT * FROM locations l WHERE l.user_id = :customerId",
            nativeQuery = true)
    Collection<Location> findAllByUserId(@Param("customerId") Long customerId);
}
