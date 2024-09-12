package com.example.app.Repositories;

import com.example.app.Entities.Candidato;
import com.example.app.Enum.StatusCandidato;
import com.example.app.Enum.StatusEleitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findByPrefeitoName(String nomePrefeito, int cargo, StatusCandidato status);
    List<Candidato> findByVereadorName(String nomeVereador, int cargo, StatusCandidato status);

    void updateUserStatus(Long userId, StatusEleitor newStatus);
}
