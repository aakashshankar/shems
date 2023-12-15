package com.shems.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {

    @Id
    @SequenceGenerator(name = "event_generator", sequenceName = "events_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "event_generator")
    private Long id;

    @ManyToOne
    private Device device;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String value;
}
