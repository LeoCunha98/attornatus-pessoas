package com.tecnico.attornatus.pessoas.repository;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoDAO extends JpaRepository<Endereco, Long> {
    List<Endereco> findAllByPessoaId(Long pessoaId);
    Endereco findByPessoaIdAndPrincipal(Long pessoaId, Boolean isPrincipal);
}
