package com.tecnico.attornatus.pessoas.service;

import com.google.gson.Gson;
import com.tecnico.attornatus.pessoas.client.ClientUtils;
import com.tecnico.attornatus.pessoas.domain.Endereco;
import com.tecnico.attornatus.pessoas.domain.Pessoa;
import com.tecnico.attornatus.pessoas.repository.EnderecoDAO;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoDTO;
import com.tecnico.attornatus.pessoas.service.dto.EnderecoViaCepDTO;
import com.tecnico.attornatus.pessoas.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    EnderecoDAO enderecoDAO;

    @Autowired
    PessoaService pessoaService;

    @Value("${viacep.endpoint}")
    private String viaCepUrl;

    public void criarEndereco(Long pessoaId, EnderecoDTO enderecoDTO) throws Exception {
        Pessoa pessoa = pessoaService.consultarPessoa(pessoaId);
        Endereco endereco = buildEndereco(pessoa, enderecoDTO, enderecoDTO.getPrincipal());
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

    private Endereco buildEndereco(Pessoa pessoa, EnderecoDTO dto, boolean isPrincipal) throws Exception {
        String url = viaCepUrl.replace("{cep}", dto.getCep());
        String responseViaCep = ClientUtils.getHttpClientResponse(url);

        EnderecoViaCepDTO enderecoViaCepDTO;
        Endereco resultado = new Endereco();
        if(responseViaCep != null) {
            Gson gson = new Gson();
            enderecoViaCepDTO = gson.fromJson(responseViaCep, EnderecoViaCepDTO.class);
            resultado.preencherEnderecoViaCep(enderecoViaCepDTO, dto);
            atualizarEnderecoPrincipal(pessoa, resultado, dto.getPrincipal());
            return resultado;
        }

        resultado.preencherEnderecoViaDTO(dto);
        atualizarEnderecoPrincipal(pessoa, resultado, dto.getPrincipal());
        return resultado;
    }

    private void atualizarEnderecoPrincipal(Pessoa pessoa, Endereco endereco, boolean isPrincipal) {
        if(!isPrincipal) {
            return;
        }

        endereco.setPrincipal(true);
        List<Endereco> enderecosAntigos = pessoa.getEnderecos();
        Endereco antigoPrincipal = enderecosAntigos.stream().filter(Endereco::getPrincipal)
                .findFirst().orElse(null);
        if(antigoPrincipal != null) {
            antigoPrincipal.setPrincipal(false);
            enderecoDAO.save(antigoPrincipal);
        }
    }

}
