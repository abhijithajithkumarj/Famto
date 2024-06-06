package com.Famto.Famto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;





@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
