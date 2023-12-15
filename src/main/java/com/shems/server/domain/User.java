package com.shems.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class User {

    @Id
    @SequenceGenerator(
            name = "customer_generator", sequenceName = "customers_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customer_generator")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String billingAddress;

    @Column(nullable = false)
    private Date signUpDate;
}
