package com.tecnico.attornatus.pessoas.service;

import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.service.exception.ObjectNotFoundException;
import com.tecnico.attornatus.pessoas.repository.EnderecoDAO;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO - AVALIAR POSSÍVEL INTEGRAÇÃO COM API PÚBLICA DE CEPS
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
        endereco.setPrincipal(false);

        //Atualiza o endereço principal caso necessário (O endereço inserido é principal)
        if(enderecoDTO.getPrincipal()){
            endereco.setPrincipal(true);

            List<Endereco> enderecosAntigos = pessoa.getEnderecos();
            Endereco antigoPrincipal = enderecosAntigos.stream().filter(Endereco::getPrincipal).findFirst().orElse(null);

            if(antigoPrincipal != null) {
                antigoPrincipal.setPrincipal(false);
                enderecoDAO.save(antigoPrincipal);
            }
        }

        endereco.setPessoa(pessoa);
        enderecoDAO.save(endereco);
    }

    public List<Endereco> listarEnderecos(Long idPessoa) {
        Pessoa pessoa = pessoaService.consultarPessoa(idPessoa);
        return pessoa.getEnderecos();
    }

    public Endereco buscarPrincipal(Long idPessoa) {
        Pessoa pessoa = pessoaService.consultarPessoa(idPessoa);
        List<Endereco> enderecos = pessoa.getEnderecos();

        return enderecos
                .stream()
                .filter(end -> end != null && Boolean.TRUE.equals(end.getPrincipal())).findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Não possui endereço principal. Id: " + idPessoa));
    }

}
