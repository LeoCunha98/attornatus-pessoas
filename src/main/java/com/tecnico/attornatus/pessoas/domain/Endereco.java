package com.tecnico.attornatus.pessoas.domain;

import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoViaCepDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;
    private String cep;
    private Integer numero;
    private String cidade;
    public Boolean principal;

    // Um endereço deve pertencer a 1 pessoa
    @ManyToOne
    private Pessoa pessoa;

    public void preencherEnderecoViaCep(EnderecoViaCepDTO enderecoViaCepDTO, EnderecoDTO enderecoDTO) {
        this.setLogradouro(enderecoViaCepDTO.getLogradouro());
        this.setCidade(enderecoViaCepDTO.getLocalidade());
        this.setNumero(enderecoDTO.getNumero());
        this.setCep(enderecoDTO.getCep());
        this.setPrincipal(false);
    }

    public void preencherEnderecoViaDTO(EnderecoDTO dto) {
        this.setLogradouro(dto.getLogradouro());
        this.setCidade(dto.getCidade());
        this.setNumero(dto.getNumero());
        this.setCep(dto.getCep());
        this.setPrincipal(false);
    }
}
