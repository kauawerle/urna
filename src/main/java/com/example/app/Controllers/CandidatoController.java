package com.example.app.Controllers;

import com.example.app.Entities.Candidato;
import com.example.app.Entities.Eleitor;
import com.example.app.Enum.StatusCandidato;
import com.example.app.Services.CandidatoService;
import com.example.app.Services.EleitorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/candidato")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid Candidato candidato){
        try {
            String result = this.candidatoService.saveCandidato(candidato);
            candidato.setStatus(StatusCandidato.ATIVO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Erro:" + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Candidato candidato, @PathVariable long id){
        try {
            String result = this.candidatoService.update(candidato, id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Deu erro! "+e.getMessage(), HttpStatus.BAD_REQUEST );
        }
    }

    @GetMapping("/findAllPrefeito")
    public ResponseEntity<List<Candidato>> findAllPrefeito(){
        try {
            List<Candidato> lista = this.candidatoService.findAllPrefeito();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }
    @GetMapping("/findAllVereador")
    public ResponseEntity<List<Candidato>> findAllVereador(){
        try {
            List<Candidato> lista = this.candidatoService.findAllVereador();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> delete( Candidato candidato, @PathVariable long id){
        try {
            String mensagem = this.candidatoService.deleteCandidato(candidato, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }
}
