package com.tecnico.attornatus.pessoas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo deve ser preenchido!")
    private String nome;

    private Date dataNascimento;

    //Uma pessoa pode possuir 1 ou N endereços
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_id")
    private List<Endereco> enderecos = new ArrayList<>();

    public Pessoa(String nome, String dataNascimento) throws ParseException {
        this.nome = nome;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        this.dataNascimento = formato.parse(dataNascimento);
    }
}
