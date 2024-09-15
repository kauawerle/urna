package com.example.app.Entities;

import com.example.app.Enum.Cargo;
import com.example.app.Enum.StatusCandidato;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String nome_completo;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 000.000.000-00")
    private String cpf;

    @NotNull(message = "O campo código é obrigatório")
    @Column(unique = true, nullable = false)
    private Long codigoUnico;

    @NotNull
    private Cargo cargo;

    @Enumerated(EnumType.STRING)
    private StatusCandidato status;

    @Transient
    private long votos;
}
