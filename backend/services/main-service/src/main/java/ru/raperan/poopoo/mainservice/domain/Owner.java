package ru.raperan.poopoo.mainservice.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    private TrackCollection favoriteTracks;

    @Column(name = "email")
    private String email;

}
