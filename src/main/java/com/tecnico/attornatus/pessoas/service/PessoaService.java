package com.tecnico.attornatus.pessoas.service;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.repository.EnderecoDAO;
import com.tecnico.attornatus.pessoas.repository.PessoaDAO;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import com.tecnico.attornatus.pessoas.service.dto.PessoaDTO;
import com.tecnico.attornatus.pessoas.service.exception.NullArgumentException;
import com.tecnico.attornatus.pessoas.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

//TODO - CRIAÇÃO DE TESTES UNITÁRIOS
@Service
public class PessoaService {

    @Autowired
    PessoaDAO pessoaDAO;

    @Autowired
    EnderecoDAO enderecoDAO;

    public void criarPessoa(PessoaDTO pessoaDTO) throws ParseException {
        Pessoa pessoa = new Pessoa(pessoaDTO.getNome(), pessoaDTO.getDataNascimento());
        pessoa.setEnderecos(EnderecoDTO.toDomainList(pessoaDTO.getEnderecos()));

        pessoaDAO.save(pessoa);
    }

    public void editarPessoa(Long id, PessoaDTO pessoaDTO) throws ParseException {
        if(pessoaDTO.getNome() == null && pessoaDTO.getEnderecos() == null && pessoaDTO.getDataNascimento() == null) {
            throw new NullArgumentException("É preciso enviar ao menos um campo para alteração.");
        }

        Pessoa pessoa = pessoaDAO.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Pessoa não encontrada! Id: " + id));

        //Exclusão de endereços antigos, mantendo apenas o(s) novo(s)
        if(!pessoaDTO.getEnderecos().isEmpty()) {
            List<Endereco> enderecosAntigos = enderecoDAO.findAllByPessoaId(id);
            enderecosAntigos.forEach(end -> enderecoDAO.delete(end));
            pessoa.setEnderecos(EnderecoDTO.toDomainList(pessoaDTO.getEnderecos()));
        }

        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
        pessoaDAO.save(pessoa);
    }

    public Pessoa consultarPessoa(Long id) {
        return pessoaDAO.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Pessoa não encontrada! Id: " + id));
    }

    public List<Pessoa> consultarPessoas() {
        return pessoaDAO.findAll();
    }
}
