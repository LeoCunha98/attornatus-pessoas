package com.tecnico.attornatus.pessoas.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDateTime dataNascimento;

    //Uma pessoa pode possuir 1 ou N endereços
    @ManyToMany(mappedBy = "residentes")
    private List<Endereco> enderecos = new ArrayList<>();
}
