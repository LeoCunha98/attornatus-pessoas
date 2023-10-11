package com.tecnico.attornatus.pessoas.service.dto;

import com.tecnico.attornatus.pessoas.domain.Pessoa;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PessoaDTO {
    private long id;
    private String nome;
    private String dataNascimento;
    private List<EnderecoDTO> enderecos;

    public static PessoaDTO fromDomain(Pessoa pessoa) {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome(pessoa.getNome());
        pessoaDTO.setDataNascimento(pessoa.getDataNascimento().toString());
        pessoaDTO.setId(pessoa.getId());

        List<EnderecoDTO> enderecoDTOS = new ArrayList<>();
        pessoa.getEnderecos().forEach(endereco -> enderecoDTOS.add(EnderecoDTO.fromDomain(endereco)));
        pessoaDTO.setEnderecos(enderecoDTOS);

        return pessoaDTO;
    }

    public void toDomain() {

    }
}
