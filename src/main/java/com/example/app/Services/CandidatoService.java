package com.example.app.Services;

import com.example.app.Entities.Candidato;
import com.example.app.Entities.Candidato;
import com.example.app.Entities.Candidato;
import com.example.app.Entities.Eleitor;
import com.example.app.Enum.StatusCandidato;
import com.example.app.Enum.StatusCandidato;
import com.example.app.Enum.StatusEleitor;
import com.example.app.Repositories.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    public String saveCandidato(Candidato candidato) {
        this.candidatoRepository.save(candidato);
        return "Candidato cadastrado!";
    }

    public String deleteCandidato(Candidato candidatoUpdated, long id) {
        Optional<Candidato> candidatoOptional =
                this.candidatoRepository.findById(id);

        if (candidatoOptional.isPresent()) {
            Candidato candidato = candidatoOptional.get();
            if(candidato.getStatus() != StatusCandidato.INATIVO) {
                candidato.setStatus(StatusCandidato.INATIVO);
                this.candidatoRepository.updateUserStatus(candidato.getId(), candidato.getStatus());
            } else{
                return "Candidato já foi excluido";
            }
        }
        return "Candidato removido";
    }

    public String update(Candidato candidatoUpdated, long id) {
        Optional<Candidato> candidatoOptional =
                this.candidatoRepository.findById(id);

        if(candidatoOptional.isPresent()) {
            Candidato candidato = candidatoOptional.get();
            candidato.setNome_completo(
                    candidato.getNome_completo()
            );
            candidato.setCpf(
                    candidato.getCpf()
            );
            candidato.setCargo(
                    candidato.getCargo()
            );
            candidato.setCodigoUnico(
                    candidato.getCodigoUnico()
            );
            candidato.setStatus(
                    StatusCandidato.ATIVO
            );
            this.candidatoRepository.save(candidato);
        }
        return "Candidato atualizado";
    }

    public List<Candidato> findAllPrefeito() {
        return this.candidatoRepository.findByPrefeitoName(1, StatusCandidato.ATIVO);
    }
    public List<Candidato> findAllVereador() {
        return this.candidatoRepository.findByVereadorName(2, StatusCandidato.ATIVO);
    }
}
