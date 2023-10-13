package com.tecnico.attornatus.pessoas.resource;

import com.tecnico.attornatus.pessoas.service.EnderecoService;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos/")
public class EnderecoResource {

    @Autowired
    EnderecoService enderecoService;

    @PostMapping("{idPessoa}")
    public ResponseEntity<?> criar(@PathVariable Long idPessoa, @RequestBody @Valid EnderecoDTO enderecoDTO) {
        enderecoService.criarEndereco(idPessoa, enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //TODO - Buscar todos endereços vinculados a pessoa do Id desejado
    @GetMapping("{idPessoa}")
    public void listar(@RequestParam Long idPessoa) {

    }

    //TODO - Buscar na lista de endereços da pessoa do Id desejado qual contém "principal: true" -> criar query
    @GetMapping("principal/{idPessoa}")
    public void principal(@RequestParam Long idPessoa) {

    }
}
