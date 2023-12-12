package com.shems.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "locations")
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Double squareFootage;

    @Column(nullable = false)
    private Integer numberOfBedrooms;

    @Column(nullable = false)
    private Integer numberOfOccupants;

    @Column(nullable = false)
    private String zipCode;
}
