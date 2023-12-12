package com.shems.server.dao;

import com.shems.server.domain.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM customers u WHERE u.id = :id", nativeQuery = true)
    @Nonnull
    Optional<User> findById(@Param("id") @Nonnull Long id);
}
