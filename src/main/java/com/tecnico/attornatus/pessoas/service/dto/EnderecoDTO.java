package com.tecnico.attornatus.pessoas.service.dto;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import lombok.Data;

@Data
public class EnderecoDTO {
    private String logradouro;
    private String cep;
    private Integer numero;
    private String cidade;
    private Boolean principal;

    public Endereco toDomain() {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(this.logradouro);
        endereco.setCep(this.cep);
        endereco.setNumero(this.numero);
        endereco.setCidade(this.cidade);
        return endereco;
    }
}
