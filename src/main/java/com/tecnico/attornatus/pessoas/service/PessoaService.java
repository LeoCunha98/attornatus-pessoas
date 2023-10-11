package com.tecnico.attornatus.pessoas.service;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.repository.PessoaDAO;
import com.tecnico.attornatus.pessoas.service.dto.PessoaDTO;
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
        List<Endereco> enderecos = new ArrayList<>();

        Pessoa pessoa = new Pessoa(pessoaDTO.getNome(), pessoaDTO.getDataNascimento());
        pessoaDTO.getEnderecos().forEach(enderecoDTO -> enderecos.add(enderecoDTO.toDomain()));
        definirEnderecoPrincipal(enderecos);
        pessoa.setEnderecos(enderecos);
        pessoaDAO.save(pessoa);
    }

    private void definirEnderecoPrincipal(List<Endereco> enderecos) {
        enderecos.get(0).setPrincipal(true);
    }

}
