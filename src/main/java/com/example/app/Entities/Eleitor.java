package com.example.app.Entities;

import com.example.app.Enum.StatusEleitor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "t_eleitor")
public class Eleitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String nome;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 000.000.000-00")
    @Column (nullable = true)
    private String cpf;

    @NotBlank
    private String profissao;

    @NotBlank
    @Pattern(regexp = "\+55\s?\(?\d{2}\)?\s?\d{5}-?\d{4}", message = "Número de celular inválido")
    private String celular;

    @NotBlank
    @Pattern(regexp = "\(?\d{2}\)?\s?\d{4}-?\d{4}", message = "Número de telefone fixo inválido")
    private String telefone;

    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    private StatusEleitor status;



}
