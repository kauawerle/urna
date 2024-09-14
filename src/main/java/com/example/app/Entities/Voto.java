package com.example.app.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private LocalDateTime dataHora;

    @OneToOne
    @JsonIgnoreProperties
    private Candidato candidatoPrefeito;

    @OneToOne
    @JsonIgnoreProperties
    private Candidato candidatoVereador;

    @NotNull
    @Column(nullable = false, unique = true)
    private UUID hash;

    @PrePersist
    private void gerarHash() {
        this.hash = UUID.randomUUID();
    }

}
