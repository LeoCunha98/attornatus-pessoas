package com.tecnico.attornatus.pessoas.service.dto;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EnderecoDTO {

    @NotBlank(message = "O logradouro deve ser informado.")
    private String logradouro;

    @Size(min = 8, message = "O cep deve conter 8 dígitos numéricos.")
    @Digits(fraction = 0, integer = 8, message = "O cep deve conter 8 dígitos numéricos.")
    private String cep;

    @NotNull(message = "O número da residência deve ser informado.")
    private Integer numero;

    @NotBlank(message = "A cidade deve ser informada.")
    private String cidade;

    private Boolean principal = false;

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
