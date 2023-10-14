package com.tecnico.attornatus.pessoas.service.dto;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import lombok.Data;

@Data
public class EnderecoViaCepDTO {
    private String logradouro;
    private String localidade;

    public Endereco toEndereco() {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(this.logradouro);
        endereco.setCidade(this.localidade);
        return endereco;
    }
}
