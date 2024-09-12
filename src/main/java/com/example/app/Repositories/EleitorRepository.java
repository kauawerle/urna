package com.example.app.Repositories;

import com.example.app.Entities.Candidato;
import com.example.app.Entities.Eleitor;
import com.example.app.Enum.StatusCandidato;
import com.example.app.Enum.StatusEleitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {
    List<Eleitor> findByEleitorAtivo(StatusEleitor status);

    void updateUserEmailAndCpf(Long userId, String email, String cpf);
    void updateUserStatus(Long userId, StatusEleitor newStatus);
}
