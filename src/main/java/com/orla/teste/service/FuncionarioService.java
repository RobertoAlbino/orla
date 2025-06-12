package com.orla.teste.service;

import com.orla.teste.dto.FuncionarioCriacaoDTO;
import com.orla.teste.dto.FuncionarioDTO;
import com.orla.teste.mapper.FuncionarioMapper;
import com.orla.teste.repository.FuncionarioRepository;
import com.orla.teste.repository.entities.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public FuncionarioDTO criar(FuncionarioCriacaoDTO dto) {
        Funcionario entidade = new Funcionario(null, dto.nome(), dto.cpf(), dto.email(), dto.salario());
        entidade.validar();
        entidade = funcionarioRepository.save(entidade);
        return FuncionarioMapper.dto(entidade);
    }

    public FuncionarioDTO buscar(Long id) {
        return funcionarioRepository.findById(id).map(FuncionarioMapper::dto)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Funcionario não encontrado"));
    }

    public Page<FuncionarioDTO> listar(Pageable pageable) {
        return funcionarioRepository.findAll(pageable).map(FuncionarioMapper::dto);
    }

}
