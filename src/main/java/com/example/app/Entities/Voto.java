package com.example.app.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voto {
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
