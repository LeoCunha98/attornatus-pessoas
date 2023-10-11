package com.tecnico.attornatus.pessoas.resource;

import com.tecnico.attornatus.pessoas.repository.EnderecoDAO;
import com.tecnico.attornatus.pessoas.service.PessoaService;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos/")
public class EnderecoResource {

    @Autowired
    PessoaService pessoaService;

    @Autowired
    EnderecoDAO enderecoDAO;

    //TODO - Criar um endereço e vincular a pessoa do Id desejado.
    @PostMapping("/{idPessoa}")
    public void criar(@RequestParam Integer idPessoa, @RequestBody @Valid EnderecoDTO enderecoDTO) {

    }

    //TODO - Buscar todos endereços vinculados a pessoa do Id desejado
    @GetMapping("/{idPessoa}")
    public void listar(@RequestParam Integer idPessoa) {

    }

    //TODO - Buscar na lista de endereços da pessoa do Id desejado qual contém "principal: true" -> criar query
    @GetMapping("principal/{idPessoa}")
    public void principal(@RequestParam Integer idPessoa) {

    }
}
