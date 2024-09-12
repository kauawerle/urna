package com.example.app.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Apuracao {
    private int totalVotos;
    private List<Candidato> candidatosPrefeito;
    private List<Candidato> candidatosVereador;
}
