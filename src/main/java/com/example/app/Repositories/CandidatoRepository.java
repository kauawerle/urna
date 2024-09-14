package com.example.app.Repositories;

import com.example.app.Entities.Candidato;
import com.example.app.Enum.StatusCandidato;
import com.example.app.Enum.StatusEleitor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    @Query("SELECT c FROM Candidato c WHERE c.cargo = :cargo AND c.status = :status AND c.cargo = 1")
    List<Candidato> findByPrefeito(int cargo, StatusCandidato status);

    @Query("SELECT c FROM Candidato c WHERE c.cargo = :cargo AND c.status = :status AND c.cargo = 2")
    List<Candidato> findByVereador(int cargo, StatusCandidato status);

    @Modifying
    @Transactional
    @Query("UPDATE Candidato c SET c.status = :newStatus WHERE c.id = :userId")
    int updateUserStatus(Long userId, StatusCandidato newStatus);
}
