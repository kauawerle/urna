package com.example.app.Services;

import com.example.app.Entities.Apuracao;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
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
        } else if (eleitor.getStatus() == StatusEleitor.APTO) {
            eleitor.setStatus(StatusEleitor.VOTOU);
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

    public Apuracao realizarApuracao() {
       List<Candidato> prefeito = this.candidatoRepository.findByCargoAndStatus(Cargo.PREFEITO, StatusCandidato.ATIVO);
       List<Candidato> vereador = this.candidatoRepository.findByCargoAndStatus(Cargo.VEREADOR, StatusCandidato.ATIVO);

       Apuracao apuracao = new Apuracao();
       apuracao.setCandidatosPrefeito(prefeito);
       apuracao.setCandidatosVereador(vereador);

       apuracao.setTotalVotos((int) votoRepository.count());

       for(Candidato prefeitoCurrent: prefeito){
           int votos = votoRepository.countByCandidatoPrefeitoId(prefeitoCurrent.getId());
           prefeitoCurrent.setVotos(votos);
       }
        for(Candidato vereadorCurrent: vereador){
            int votos = votoRepository.countByCandidatoVereadorId(vereadorCurrent.getId());
            vereadorCurrent.setVotos(votos);
        }

        Collections.sort(prefeito, (list1, list2) -> Integer.compare(list2.getVotos(), list1.getVotos()));
        Collections.sort(vereador, (list1, list2) -> Integer.compare(list2.getVotos(), list1.getVotos()));

        return apuracao;
    }
}
