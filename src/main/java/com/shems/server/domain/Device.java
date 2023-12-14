package com.shems.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "devices")
@Getter
@Setter
public class Device {

    @Id
    @SequenceGenerator(name = "device_id_generator", sequenceName = "device_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "device_id_generator")
    private Long id;

    @Column(nullable = false)
    private Integer modelNumber;

    @ManyToOne
    private Location location;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Date enrollmentDate;
}
