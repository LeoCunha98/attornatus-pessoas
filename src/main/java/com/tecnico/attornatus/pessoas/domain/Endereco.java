package com.tecnico.attornatus.pessoas.domain;

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
}
