package com.tecnico.attornatus.pessoas.service;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.repository.EnderecoDAO;
import com.tecnico.attornatus.pessoas.repository.PessoaDAO;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import com.tecnico.attornatus.pessoas.service.dto.PessoaDTO;
import com.tecnico.attornatus.pessoas.service.exception.NullArgumentException;
import com.tecnico.attornatus.pessoas.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sound.midi.InvalidMidiDataException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaDAO pessoaDAO;


    private static final Long PESSOA1_ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarPessoaSucesso() throws ParseException {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Nome Teste");
        pessoaDTO.setDataNascimento("01/01/1990");
        pessoaDTO.setEnderecos(Collections.emptyList());

        pessoaService.criarPessoa(pessoaDTO);

        verify(pessoaDAO, times(1)).save(any(Pessoa.class));
    }

    @Test
    void testCriarPessoaDataNascimentoIncorreta() {
        // Arrange
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Nome Teste");
        pessoaDTO.setDataNascimento("01-01-1990");  // Formato de data incorreto

        // Act and Assert
        assertThrows(ParseException.class, () -> pessoaService.criarPessoa(pessoaDTO));
    }

    @Test
    void testEditarPessoaSucesso() throws ParseException {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Nome Teste Editado");
        pessoaDTO.setDataNascimento("02/02/2000");
        pessoaDTO.setEnderecos(Collections.emptyList());

        Pessoa pessoaExistente = new Pessoa("Nome Antigo", "01/01/1990");

        when(pessoaDAO.findById(PESSOA1_ID)).thenReturn(Optional.of(pessoaExistente));

        pessoaService.editarPessoa(PESSOA1_ID, pessoaDTO);

        verify(pessoaDAO, times(1)).save(any(Pessoa.class));
    }

    @Test
    void testEditarPessoaDtoVazioExcecao() {
        PessoaDTO pessoaDTO = new PessoaDTO();  // DTO vazio

        NullArgumentException exception = assertThrows(NullArgumentException.class,
                () -> pessoaService.editarPessoa(PESSOA1_ID, pessoaDTO));
        assertEquals("É preciso enviar ao menos um campo para alteração.", exception.getMessage());
    }

    @Test
    void testConsultarPessoaSucesso() throws ParseException {
        Pessoa pessoaExistente = new Pessoa("Nome Teste", "01/01/1990");

        when(pessoaDAO.findById(PESSOA1_ID)).thenReturn(Optional.of(pessoaExistente));

        Pessoa resultado = pessoaService.consultarPessoa(PESSOA1_ID);

        assertEquals(pessoaExistente, resultado);
    }

    @Test
    void testConsultarPessoaExcecao() {
        when(pessoaDAO.findById(PESSOA1_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> pessoaService.consultarPessoa(PESSOA1_ID));
    }
}
