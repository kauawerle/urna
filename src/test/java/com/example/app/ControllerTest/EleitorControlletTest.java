package com.example.app.ControllerTest;

import com.example.app.Controllers.EleitorController;
import com.example.app.Entities.Eleitor;
import com.example.app.Enum.StatusEleitor;
import com.example.app.Repositories.EleitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class EleitorControlletTest {

    @Autowired
    EleitorController eleitorController;

    @MockBean
    EleitorRepository eleitorRepository;

    Eleitor eleitor = new Eleitor();

    @BeforeEach
    void setup() {
        eleitor.setId(1);
        eleitor.setStatus(StatusEleitor.INATIVO);
        eleitor.setCpf("111.370.899-97");
        eleitor.setNome("Pablo Marçal");
        eleitor.setProfissao("Marceneiro");
        eleitor.setCelular("11 99777-1234");
        eleitor.setTelefone("11 99412-1234");
        eleitor.setEmail("pablomarcal@gmail.com");

        Mockito.when(eleitorRepository.save(Mockito.any(Eleitor.class)))
                .thenReturn(eleitor);
    }

    @Test
    void saveEleitor() {
        ResponseEntity<String> retorno = eleitorController.save(eleitor);
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
    }

    @Test
    @DisplayName("ELEITOR # RESPONSE: 'Nome e CPF inválidos' # EXCEPTION")
    void cenarioEleitorSalvarException() {
        // eleitor invalido
        Eleitor eleitorInvalido = new Eleitor();
        eleitorInvalido.setNome("Invalido");
        eleitorInvalido.setCpf("123.456.78.91");

        assertThrows(Exception.class,()->{
            ResponseEntity<String> retorno = eleitorController.save(eleitorInvalido);
        });
    }
    @Test
    @DisplayName("NADA ENCONTRADO NO ELEITOR BAD REQUEST")
    void notFoundEleitor() {
        Eleitor eleitorInvalido = null;

        ResponseEntity<String> retorno = eleitorController.save(eleitorInvalido);
        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
    }
}
