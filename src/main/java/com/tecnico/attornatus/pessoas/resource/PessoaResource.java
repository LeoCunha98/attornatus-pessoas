package com.tecnico.attornatus.pessoas.resource;

import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.service.PessoaService;
import com.tecnico.attornatus.pessoas.service.dto.PessoaDTO;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/pessoas/")
public class PessoaResource {

    @Autowired
    PessoaService pessoaService;

    @PostMapping
    public ResponseEntity criar(@Valid @RequestBody PessoaDTO pessoaDTO) throws ParseException {
        pessoaService.criarPessoa(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity editar(@PathVariable Integer id, @Valid @RequestBody PessoaDTO pessoaDTO) {
        pessoaService.editarPessoa(id, pessoaDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> consultar(@PathVariable Integer id) {
        PessoaDTO pessoa = pessoaService.consultarPessoa(id);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listar() {
        List<PessoaDTO> pessoas = pessoaService.consultarPessoas();
        return ResponseEntity.ok(pessoas);
    }
}
