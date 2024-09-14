package com.example.app.Repositories;

import com.example.app.Entities.Candidato;
import com.example.app.Entities.Eleitor;
import com.example.app.Enum.StatusCandidato;
import com.example.app.Enum.StatusEleitor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {
    @Query("SELECT e FROM Eleitor e WHERE e.status = :status")
    List<Eleitor> findByStauts(StatusEleitor status);

    @Modifying
    @Transactional
    @Query("UPDATE Eleitor e SET e.email = :email, e.cpf = :cpf WHERE e.id = :userId")
    int updateUserEmailAndCpf(Long userId, String email, String cpf);

    @Modifying
    @Transactional
    @Query("UPDATE Eleitor e SET e.status = :newStatus WHERE e.id = :userId")
    int updateUserStatus(Long userId, StatusEleitor newStatus);
}
