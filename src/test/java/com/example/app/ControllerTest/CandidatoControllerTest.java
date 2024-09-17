package com.example.app.ControllerTest;

import com.example.app.Controllers.CandidatoController;
import com.example.app.Entities.Candidato;
import com.example.app.Enum.Cargo;
import com.example.app.Enum.StatusCandidato;
import com.example.app.Repositories.CandidatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CandidatoControllerTest {

    @Autowired
    CandidatoController candidatoController;

    @MockBean
    CandidatoRepository candidatoRepository;

    Candidato candidato = new Candidato();

    @BeforeEach
    void setup(){
        candidato.setId(1);
        candidato.setStatus(StatusCandidato.ATIVO);
        candidato.setCpf("121.893.509-05");
        candidato.setNome_completo("Kwanza Hype");
        candidato.setCodigoUnico(33L);
        candidato.setCargo(Cargo.PREFEITO);
        candidato.setVotos(0);

        Mockito.when(candidatoRepository.save(Mockito.any(Candidato.class)))
                .thenReturn(candidato);
    }

    @Test
    void saveCandidato() {
        ResponseEntity<String> retorno = candidatoController.save(candidato);
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
    }

    @Test
    @DisplayName("CANDIDATO # RESPONSE: 'Nome e CPF inválidos' # EXCEPTION")
    void cenarioCandidatoSalvarException() {
        // candidato invalido
        Candidato candidatoInvalido = new Candidato();
        candidatoInvalido.setNome_completo("Invalido");
        candidatoInvalido.setCpf("123.456.89.00");
        candidatoInvalido.setCodigoUnico(23L);
        candidatoInvalido.setCargo(Cargo.PREFEITO);

        assertThrows(Exception.class,()->{
            ResponseEntity<String> retorno = candidatoController.save(candidatoInvalido);
        });
    }
    @Test
    @DisplayName("NADA ENCONTRADO NO CANDIDATO BAD REQUEST")
    void notFoundCandidato() {
        Candidato candidatoInvalido = null;

        ResponseEntity<String> retorno = candidatoController.save(candidatoInvalido);
        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
    }
}
