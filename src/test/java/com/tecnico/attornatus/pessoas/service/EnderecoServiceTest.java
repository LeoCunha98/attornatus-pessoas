package com.tecnico.attornatus.pessoas.service;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.repository.EnderecoDAO;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import com.tecnico.attornatus.pessoas.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoDAO enderecoDAO;

    @Mock
    private PessoaService pessoaService;

    private static final Long PESSOA1_ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarEnderecoSucesso() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setLogradouro("Rua Teste");
        enderecoDTO.setCidade("Cidade Teste");
        enderecoDTO.setNumero(123);
        enderecoDTO.setCep("12345-678");

        // Mock PessoaService para retornar uma Pessoa válida
        Pessoa pessoa = new Pessoa();
        when(pessoaService.consultarPessoa(PESSOA1_ID)).thenReturn(pessoa);

        enderecoService.criarEndereco(PESSOA1_ID, enderecoDTO);

        verify(enderecoDAO, times(1)).save(any(Endereco.class));
    }

    @Test
    void testCriarEnderecoExcecao() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setLogradouro("Rua Teste");
        enderecoDTO.setCidade("Cidade Teste");
        enderecoDTO.setNumero(123);
        enderecoDTO.setCep("12345-678");

        // Mock PessoaService para lançar uma exceção ObjectNotFoundException
        when(pessoaService.consultarPessoa(PESSOA1_ID)).thenThrow(new ObjectNotFoundException("Pessoa não encontrada."));

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> enderecoService.criarEndereco(PESSOA1_ID, enderecoDTO));

        assertEquals("Pessoa não encontrada.", exception.getMessage());
    }

    @Test
    void testListarEnderecosSucesso() {
        // Mock PessoaService para retornar uma Pessoa válida
        Pessoa pessoa = new Pessoa();
        pessoa.setEnderecos(Collections.singletonList(new Endereco()));
        when(pessoaService.consultarPessoa(PESSOA1_ID)).thenReturn(pessoa);

        List<Endereco> enderecos = enderecoService.listarEnderecos(PESSOA1_ID);

        assertEquals(1, enderecos.size());
    }

    @Test
    void testBuscarPrincipalSucesso() {
        Endereco enderecoPrincipal = new Endereco();
        enderecoPrincipal.setPrincipal(true);

        // Mock PessoaService para retornar uma Pessoa válida com um endereço principal
        Pessoa pessoa = new Pessoa();
        pessoa.setEnderecos(Collections.singletonList(enderecoPrincipal));
        when(pessoaService.consultarPessoa(PESSOA1_ID)).thenReturn(pessoa);

        Endereco resultado = enderecoService.buscarPrincipal(PESSOA1_ID);

        assertEquals(enderecoPrincipal, resultado);
    }

    @Test
    void testBuscarPrincipalExcecao() {
        // Mock PessoaService para retornar uma Pessoa válida sem endereço principal
        Pessoa pessoa = new Pessoa();
        pessoa.setEnderecos(Collections.singletonList(new Endereco()));
        when(pessoaService.consultarPessoa(PESSOA1_ID)).thenReturn(pessoa);

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> enderecoService.buscarPrincipal(PESSOA1_ID));

        assertEquals("Não possui endereço principal. Id: " + PESSOA1_ID, exception.getMessage());
    }
}
