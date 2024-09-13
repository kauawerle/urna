package com.example.app.Controllers;

import com.example.app.Entities.Eleitor;
import com.example.app.Services.EleitorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/eleitor")
public class EleitorController {

    @Autowired
    private EleitorService eleitorService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid Eleitor eleitor){
        try {
            String result = this.eleitorService.saveEleitor(eleitor);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Erro:" + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Eleitor eleitor, @PathVariable long id){
        try {
            String result = this.eleitorService.updateEleitorifDontHaveCpfAndEmail(eleitor, id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Deu erro!"+e.getMessage(), HttpStatus.BAD_REQUEST );
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Eleitor>> findAll(){
        try {
            List<Eleitor> lista = this.eleitorService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete( Eleitor eleitor, @PathVariable long id){
        try {
            String mensagem = this.eleitorService.deleteEleitor(eleitor, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }
}
