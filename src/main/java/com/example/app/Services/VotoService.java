package com.example.app.Services;

import com.example.app.Entities.Candidato;
import com.example.app.Entities.Eleitor;
import com.example.app.Entities.Voto;
import com.example.app.Enum.Cargo;
import com.example.app.Enum.StatusCandidato;
import com.example.app.Enum.StatusEleitor;
import com.example.app.Repositories.CandidatoRepository;
import com.example.app.Repositories.EleitorRepository;
import com.example.app.Repositories.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VotoService {
    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private EleitorRepository eleitorRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    public String votar(Voto voto, long id_eleitor) {
        Optional<Eleitor> eleitorOptional =
                this.eleitorRepository.findById(id_eleitor);

        Eleitor eleitor = eleitorOptional.get();

        ValidarEleitor(eleitor);

        ValidacaodeVoto(voto);

        voto.setHash(UUID.randomUUID().toString());
        voto.setDataHora(LocalDate.now());
        this.votoRepository.save(voto);

        return voto.getHash().toString();
    }

    public void ValidarEleitor(Eleitor eleitor) {
        if(eleitor.getStatus() == StatusEleitor.PENDENTE) {
            eleitor.setStatus(StatusEleitor.BLOQUEADO);
            throw  new IllegalArgumentException("Esse eleitor não pode votar, ele será bloqueado!!! :( 😒");
        } else if (eleitor.getStatus() == StatusEleitor.BLOQUEADO) {
                throw  new IllegalArgumentException("Esse eleitor está bloqueado");
        } else if (eleitor.getStatus() == StatusEleitor.INATIVO) {
            throw new IllegalArgumentException("Esse eleitor está inativo");
        } else if (eleitor.getStatus() == StatusEleitor.VOTOU) {
            throw new IllegalArgumentException("Esse eleitor já votou");
        }
    }

    public void ValidacaodeVoto(Voto voto){
        Candidato prefeito = candidatoRepository.findById(voto.getCandidatoPrefeito().getId()).get();
        Candidato vereador = candidatoRepository.findById(voto.getCandidatoVereador().getId()).get();
        if(prefeito.getCargo() == Cargo.PREFEITO && vereador.getCargo() == Cargo.VEREADOR){
            ValidarCandidato(prefeito);
            ValidarCandidato(vereador);
        } else {
            throw new IllegalArgumentException("Informações incorretas do voto");
        }
    }

    public void ValidarCandidato(Candidato candidato){
        if(candidato.getStatus() == StatusCandidato.INATIVO){
            throw new IllegalArgumentException("Candidato inativo");
        }
    }
}
