package com.example.app.Services;

import com.example.app.Entities.Eleitor;
import com.example.app.Enum.StatusEleitor;
import com.example.app.Repositories.EleitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EleitorService {

    @Autowired
    private EleitorRepository eleitorRepository;

    public String saveEleitor(Eleitor eleitor) {
        this.eleitorRepository.save(eleitor);
        return "Eleitor cadastrado!";
    }

    public String updateEleitorifDontHaveCpfAndEmail(Eleitor eleitorUpdated, long id) {
        Optional<Eleitor> eleitorOptional =
                this.eleitorRepository.findById(id);

        if(eleitorOptional.isPresent()) {
            Eleitor eleitor = eleitorOptional.get();
            eleitor.setNome(
                    eleitor.getNome()
            );
            eleitor.setCelular(
                    eleitor.getCelular()
            );
            eleitor.setProfissao(
                    eleitor.getProfissao()
            );
            eleitor.setTelefone(
                    eleitor.getTelefone()
            );
            eleitor.setEmail(
                    eleitorUpdated.getEmail()
            );
            eleitor.setCpf(
                    eleitorUpdated.getCpf()
            );
            eleitor.setStatus(
                    StatusEleitor.APTO
            );

            this.eleitorRepository.updateUserEmailAndCpf(eleitor.getId(), eleitor.getEmail(), eleitor.getCpf());
            this.eleitorRepository.updateUserStatus(eleitor.getId(), eleitor.getStatus());
        }
        return "Eleitor atualizado";
    }

    public String deleteEleitor(Eleitor eleitorUpdated, long id) {
        Optional<Eleitor> eleitorOptional =
                this.eleitorRepository.findById(id);

        if (eleitorOptional.isPresent()) {
            Eleitor eleitor = eleitorOptional.get();
            if(eleitor.getStatus() != StatusEleitor.VOTOU) {
                eleitor.setStatus(StatusEleitor.INATIVO);
                this.eleitorRepository.updateUserStatus(eleitor.getId(), eleitor.getStatus());
            }
        }
        return "Eleitor removido";
    }

    public List<Eleitor> findAll() {
        return this.eleitorRepository.findByStauts(StatusEleitor.APTO);
    }
}
