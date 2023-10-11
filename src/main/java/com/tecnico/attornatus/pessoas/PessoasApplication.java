package com.tecnico.attornatus.pessoas;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.repository.EnderecoDAO;
import com.tecnico.attornatus.pessoas.repository.PessoaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class PessoasApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PessoasApplication.class, args);
	}

	@Autowired
	PessoaDAO pessoaDAO;

	@Autowired
	EnderecoDAO enderecoDAO;

	@Override
	public void run(String... args) throws Exception {
		Pessoa pessoa1 = new Pessoa("Leo", LocalDateTime.now());
		pessoaDAO.save(pessoa1);

		Pessoa pessoa2 = new Pessoa("Mazilao", LocalDateTime.now());
		pessoaDAO.save(pessoa2);

		Endereco endereco1 = new Endereco();
		endereco1.setCep("36020-130");
		endereco1.setCidade("Juiz de Fora");
		endereco1.setLogradouro("Rua Ceará");
		endereco1.setPrincipal(true);
		endereco1.setNumero(13);

		Endereco endereco2 = new Endereco();
		endereco2.setCep("36020-130");
		endereco2.setCidade("Juiz de Fora");
		endereco2.setLogradouro("Rua Ceará");
		endereco2.setPrincipal(false);
		endereco2.setNumero(14);

		List<Endereco> enderecosP1 = pessoa1.getEnderecos();
		enderecosP1.add(endereco1);
		enderecosP1.add(endereco2);
		pessoa1.setEnderecos(enderecosP1);

		pessoaDAO.save(pessoa1);
	}

}
