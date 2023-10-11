package com.tecnico.attornatus.pessoas.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    //Boleano que define o endereço principal.
    private Boolean principal;

    // Um endereço deve pertencer a 1 ou N pessoas
    @ManyToMany
    @JoinTable(name = "residentes",
            joinColumns = @JoinColumn(name = "endereco_id"),
            inverseJoinColumns = @JoinColumn(name = "pessoa_id"))
    private List<Pessoa> residentes = new ArrayList<>();
}
