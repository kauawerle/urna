package com.example.app.Repositories;

import com.example.app.Entities.Candidato;
import com.example.app.Enum.Cargo;
import com.example.app.Enum.StatusCandidato;
import com.example.app.Enum.StatusEleitor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    List<Candidato> findByCargoAndStatus(Cargo cargo, StatusCandidato status);
}
