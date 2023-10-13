package com.tecnico.attornatus.pessoas.service;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.repository.EnderecoDAO;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO - CRIAÇÃO DE TESTES UNITÁRIOS
//TODO - AVALIAR POSSÍVEL INTEGRAÇÃO COM API PÚBLICA DE CEPS
@Service
public class EnderecoService {
    @Autowired
    EnderecoDAO enderecoDAO;

    @Autowired
    PessoaService pessoaService;

    //TODO - TRATAR CASO EM QUE A PESSOA NÃO EXISTE -> LANÇAR EXCEÇÃO PERSONALIZADA OBJECT NOT FOUND (PESSOA)!
    //TODO - LANÇAR EXCEÇÃO PERSONALIZADA PARA BAD REQUEST
    public void criarEndereco(Long pessoaId, EnderecoDTO enderecoDTO) {
        Pessoa pessoa = pessoaService.consultarPessoa(pessoaId);

        Endereco endereco = new Endereco();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setCep(enderecoDTO.getCep());

        if(enderecoDTO.getPrincipal()){
            endereco.setPrincipal(true);
            //Atualizar antigo endereço principal
            Endereco antigoPrincipal = buscarPrincipal(pessoaId);
            antigoPrincipal.setPrincipal(false);
            enderecoDAO.save(antigoPrincipal);
        }

        endereco.setPessoa(pessoa);
        enderecoDAO.save(endereco);
    }


    //TODO - TRATAR CASO EM QUE A PESSOA NÃO EXISTE
    public List<Endereco> listarEnderecos(Long idPessoa) {
        return enderecoDAO.findAllByPessoaId(idPessoa);
    }

    //TODO - TRATAR CASOS EM QUE A PESSOA NÃO EXISTE
    public Endereco buscarPrincipal(Long idPessoa) {
        return enderecoDAO.findByPessoaIdAndPrincipalTrue(idPessoa);
    }

}
