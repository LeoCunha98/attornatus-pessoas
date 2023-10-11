package com.tecnico.attornatus.pessoas.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class PessoaDTO {
    private long id;
    private String nome;
    private String dataNascimento;
    private List<EnderecoDTO> enderecos;
}
