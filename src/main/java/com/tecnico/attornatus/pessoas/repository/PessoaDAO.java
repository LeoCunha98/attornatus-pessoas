package com.tecnico.attornatus.pessoas.repository;

import com.tecnico.attornatus.pessoas.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaDAO extends JpaRepository<Pessoa, Integer> {

}
