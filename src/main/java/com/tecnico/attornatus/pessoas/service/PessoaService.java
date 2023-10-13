package com.tecnico.attornatus.pessoas.service;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.repository.EnderecoDAO;
import com.tecnico.attornatus.pessoas.repository.PessoaDAO;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import com.tecnico.attornatus.pessoas.service.dto.PessoaDTO;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    PessoaDAO pessoaDAO;

    @Autowired
    EnderecoDAO enderecoDAO;


    public void criarPessoa(PessoaDTO pessoaDTO) throws ParseException {
        Pessoa pessoa = new Pessoa(pessoaDTO.getNome(), pessoaDTO.getDataNascimento());
        pessoa.setEnderecos(converterEnderecos(pessoaDTO.getEnderecos()));
        pessoaDAO.save(pessoa);
    }

    public void editarPessoa(Long id, PessoaDTO pessoaDTO) throws ParseException {
        Pessoa pessoa = pessoaDAO.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(Pessoa.class, "Pessoa não encontrada!"));

        if(!pessoaDTO.getEnderecos().isEmpty()) {
            List<Endereco> enderecosAntigos = enderecoDAO.findAllByPessoaId(id);
            enderecosAntigos.forEach(end -> enderecoDAO.delete(end));
            pessoa.setEnderecos(converterEnderecos(pessoaDTO.getEnderecos()));
        }

        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
        pessoaDAO.save(pessoa);
    }

    //TODO - REAVALIAR NECESSIDADE
    private List<Endereco> converterEnderecos(List<EnderecoDTO> enderecoDTOS) {
        List<Endereco> enderecos = new ArrayList<>();
        enderecoDTOS.forEach(enderecoDTO -> enderecos.add(enderecoDTO.toDomain()));
        enderecos.get(0).setPrincipal(true);
        return enderecos;
    }

    public Pessoa consultarPessoa(Long id) {
        return pessoaDAO.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(Pessoa.class, "Pessoa não encontrada!"));
    }

    public List<Pessoa> consultarPessoas() {
        return pessoaDAO.findAll();
    }
}

// TODO - CRIAÇÃO DE EXCEÇÕES PERSONALIZADAS
// TODO - CRIAÇÃO DE TESTES UNITÁRIOS