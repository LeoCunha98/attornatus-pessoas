package com.tecnico.attornatus.pessoas.resource;

import com.tecnico.attornatus.pessoas.service.EnderecoService;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("{idPessoa}")
    public ResponseEntity<List<EnderecoDTO>> listar(@PathVariable Long idPessoa) {
        List<EnderecoDTO> enderecos = new ArrayList<>();
        enderecoService.listarEnderecos(idPessoa).forEach(end -> enderecos.add(EnderecoDTO.fromDomain(end)));
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("principal/{idPessoa}")
    public ResponseEntity<EnderecoDTO> principal(@PathVariable Long idPessoa) {
        return ResponseEntity.ok(EnderecoDTO.fromDomain(enderecoService.buscarPrincipal(idPessoa)));
    }
}
