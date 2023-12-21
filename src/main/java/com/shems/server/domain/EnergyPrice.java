package com.shems.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "energy_prices")
@Getter
@Setter
public class EnergyPrice {

    @Id
    @SequenceGenerator(name = "energy_price_generator", sequenceName = "energy_prices_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "energy_price_generator")
    private Long id;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private Integer hour;

    @Column(nullable = false)
    private Double price;
}
