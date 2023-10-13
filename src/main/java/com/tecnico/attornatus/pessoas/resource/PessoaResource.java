package com.tecnico.attornatus.pessoas.resource;

import com.tecnico.attornatus.pessoas.service.EnderecoService;
import com.tecnico.attornatus.pessoas.service.PessoaService;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import com.tecnico.attornatus.pessoas.service.dto.PessoaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pessoas/")
public class PessoaResource {

    @Autowired
    PessoaService pessoaService;

    @Autowired
    EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody PessoaDTO pessoaDTO) throws ParseException {
        pessoaService.criarPessoa(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) throws ParseException {
        pessoaService.editarPessoa(id, pessoaDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> consultar(@PathVariable Long id) {
        PessoaDTO pessoa = PessoaDTO.fromDomain(pessoaService.consultarPessoa(id));
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listar() {
        List<PessoaDTO> pessoas = new ArrayList<>();
        pessoaService.consultarPessoas().forEach(pessoaDomain -> pessoas.add(PessoaDTO.fromDomain(pessoaDomain)));
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping("{id}/endereco")
    public ResponseEntity<?> criar(@PathVariable Long id, @RequestBody @Valid EnderecoDTO enderecoDTO) {
        enderecoService.criarEndereco(id, enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{id}/enderecos")
    public ResponseEntity<List<EnderecoDTO>> listar(@PathVariable Long id) {
        List<EnderecoDTO> enderecos = new ArrayList<>();
        enderecoService.listarEnderecos(id).forEach(end -> enderecos.add(EnderecoDTO.fromDomain(end)));
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("{id}/endereco/principal")
    public ResponseEntity<EnderecoDTO> principal(@PathVariable Long id) {
        return ResponseEntity.ok(EnderecoDTO.fromDomain(enderecoService.buscarPrincipal(id)));
    }
}
