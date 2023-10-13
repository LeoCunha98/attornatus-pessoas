package com.tecnico.attornatus.pessoas.service;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.repository.EnderecoDAO;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
    @Autowired
    EnderecoDAO enderecoDAO;

    @Autowired
    PessoaService pessoaService;

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

    public Endereco buscarPrincipal(Long idPessoa) {
        return enderecoDAO.findByPessoaIdAndPrincipalTrue(idPessoa);
    }
}
