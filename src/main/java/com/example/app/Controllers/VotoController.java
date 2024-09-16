package com.example.app.Controllers;

import com.example.app.Entities.Apuracao;
import com.example.app.Entities.Voto;
import com.example.app.Repositories.EleitorRepository;
import com.example.app.Services.EleitorService;
import com.example.app.Services.VotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/voto")
@RestController
@Validated
public class VotoController {
    @Autowired
    VotoService votoService;

    @Autowired
    EleitorRepository eleitorRepository;

    @PostMapping("/save/{eleitor_id}")
    public ResponseEntity<String> salvar(@Valid @RequestBody Voto voto, @PathVariable Long eleitor_id){
        try{
            String mensagem = this.votoService.votar(voto, eleitor_id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/apuracao")
    public ResponseEntity<?> apuracao() {
        try {
            Apuracao apuracao = this.votoService.realizarApuracao();
            return new ResponseEntity<>(apuracao, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
