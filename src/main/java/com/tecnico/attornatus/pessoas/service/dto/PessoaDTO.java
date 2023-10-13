package com.tecnico.attornatus.pessoas.service.dto;

import com.tecnico.attornatus.pessoas.domain.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PessoaDTO {
    private long id;

    @NotBlank(message = "O nome deve ser informado.")
    private String nome;

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$",
            message = "O formato da data deve ser \"dd/mm/aaaa\"")
    private String dataNascimento;

    @NotNull(message = "Deve ser informado ao menos um endereço.")
    private List<EnderecoDTO> enderecos;

    public static PessoaDTO fromDomain(Pessoa pessoa) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNascimento = pessoa.getDataNascimento();

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome(pessoa.getNome());
        pessoaDTO.setDataNascimento(formato.format(dataNascimento));
        pessoaDTO.setId(pessoa.getId());

        List<EnderecoDTO> enderecoDTOS = new ArrayList<>();
        pessoa.getEnderecos().forEach(endereco -> enderecoDTOS.add(EnderecoDTO.fromDomain(endereco)));
        pessoaDTO.setEnderecos(enderecoDTOS);

        return pessoaDTO;
    }

}
