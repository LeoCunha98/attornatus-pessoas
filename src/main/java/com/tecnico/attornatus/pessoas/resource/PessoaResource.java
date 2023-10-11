package com.tecnico.attornatus.pessoas.resource;

import com.tecnico.attornatus.pessoas.service.PessoaService;
import com.tecnico.attornatus.pessoas.service.dto.PessoaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

    @Autowired
    PessoaService pessoaService;

    @PostMapping
    public ResponseEntity criar(@Valid @RequestBody PessoaDTO pessoaDTO) throws ParseException {
        pessoaService.criarPessoa(pessoaDTO);
        return ResponseEntity.ok().build();
    }

    public void editar() {

    }

    public void consultar() {

    }

    public void listar() {

    }
}
