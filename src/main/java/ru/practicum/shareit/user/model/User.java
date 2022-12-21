package ru.practicum.shareit.user.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Data
@Entity
@Table(name = "users", schema = "public")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(unique = true, length = 100, nullable = false)
    private String email;
}
