package com.tecnico.attornatus.pessoas.service;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
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

    public void criarPessoa(PessoaDTO pessoaDTO) throws ParseException {
        Pessoa pessoa = new Pessoa(pessoaDTO.getNome(), pessoaDTO.getDataNascimento());
        pessoa.setEnderecos(converterEnderecos(pessoaDTO.getEnderecos()));
        pessoaDAO.save(pessoa);
    }

    //TODO - AO ALTERAR A PESSOA, OS ENDEREÇOS QUE ANTES ERAM VINCULADOS A ESSE ID ESTÃO FICANDO COM "PESSOA_ID" -> EFETUAR CORREÇÃO"
    public void editarPessoa(Integer id, PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaDAO.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(Pessoa.class, "Pessoa não encontrada!"));

        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setEnderecos(converterEnderecos(pessoaDTO.getEnderecos()));
        pessoaDAO.save(pessoa);
    }

    private List<Endereco> converterEnderecos(List<EnderecoDTO> enderecoDTOS) {
        List<Endereco> enderecos = new ArrayList<>();
        enderecoDTOS.forEach(enderecoDTO -> enderecos.add(enderecoDTO.toDomain()));
        enderecos.get(0).setPrincipal(true);
        return enderecos;
    }

    public PessoaDTO consultarPessoa(Integer id) {
        Pessoa pessoa = pessoaDAO.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(Pessoa.class, "Pessoa não encontrada!"));
        return PessoaDTO.fromDomain(pessoa);
    }

    public List<PessoaDTO> consultarPessoas() {
        List<PessoaDTO> pessoasDTO = new ArrayList<>();
        pessoaDAO.findAll().forEach(pessoa -> pessoasDTO.add(PessoaDTO.fromDomain(pessoa)));
        return pessoasDTO;
    }
}
