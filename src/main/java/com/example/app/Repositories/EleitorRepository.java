package com.example.app.Repositories;

import com.example.app.Entities.Eleitor;
import com.example.app.Enum.StatusEleitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {
    void updateUserStatus(Long userId, StatusEleitor newStatus);
}
