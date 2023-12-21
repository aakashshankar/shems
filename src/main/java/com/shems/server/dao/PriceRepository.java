package com.shems.server.dao;

import com.shems.server.domain.EnergyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PriceRepository extends JpaRepository<EnergyPrice, Long> {

    String priceQuery = """
            SELECT
              sum(ep.price * cast(e.value as decimal))
            FROM
              events e
              JOIN devices d ON e.device_id = d.id
              JOIN locations l ON d.location_id = l.id
              JOIN energy_prices ep ON ep.hour = EXTRACT(
                HOUR
                FROM
                  e.timestamp
              )
              AND ep.zip_code = l.zip_code
            WHERE
              l.user_id = :customerId
              AND e.timestamp <= :to
              AND e.timestamp >= :from
              AND e.type = 'energy use'
        """;

    String totalPriceQuery =
            """
            SELECT
              sum(ep.price * cast(e.value as decimal))
            FROM
              events e
              JOIN devices d ON e.device_id = d.id
              JOIN locations l ON d.location_id = l.id
              JOIN energy_prices ep ON ep.hour = EXTRACT(
                HOUR
                FROM
                  e.timestamp
              )
              AND ep.zip_code = l.zip_code
            WHERE
              l.user_id = :customerId
            """;

    @Query(value = priceQuery, nativeQuery = true)
    Double getTotal(@Param("customerId") Long customerId, @Param("from") Date from, @Param("to") Date to);

    @Query(value = totalPriceQuery, nativeQuery = true)
    Double getHistory(@Param("customerId") Long customerId);
}
