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

    public static EnderecoDTO fromDomain(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setPrincipal(endereco.getPrincipal());
        return enderecoDTO;
    }

    public Endereco toDomain() {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(this.logradouro);
        endereco.setCep(this.cep);
        endereco.setNumero(this.numero);
        endereco.setCidade(this.cidade);
        endereco.setPrincipal(this.principal);
        return endereco;
    }
}
