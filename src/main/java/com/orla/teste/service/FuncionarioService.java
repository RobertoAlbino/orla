package com.orla.teste.service;

import com.orla.teste.dto.FuncionarioCriacaoDTO;
import com.orla.teste.dto.FuncionarioDTO;
import com.orla.teste.mapper.FuncionarioMapper;
import com.orla.teste.repository.FuncionarioRepository;
import com.orla.teste.repository.entities.Funcionario;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public FuncionarioDTO criar(FuncionarioCriacaoDTO dto) {
        log.info("Iniciando criação de funcionário: {}", dto);
        Funcionario entidade = new Funcionario(null, dto.nome(), dto.cpf(), dto.email(), dto.salario());
        entidade.validar();
        entidade = funcionarioRepository.save(entidade);
        FuncionarioDTO resultado = FuncionarioMapper.dto(entidade);
        log.info("Funcionário criado com sucesso: {}", resultado);
        return resultado;
    }

    public FuncionarioDTO buscar(Long id) {
        log.info("Buscando funcionário com ID: {}", id);
        FuncionarioDTO dto = funcionarioRepository.findById(id)
                .map(FuncionarioMapper::dto)
                .orElseThrow(() -> {
                    log.error("Funcionário não encontrado: ID {}", id);
                    return new EntityNotFoundException("Funcionario não encontrado");
                });
        log.info("Funcionário encontrado: {}", dto);
        return dto;
    }

    public Page<FuncionarioDTO> listar(Pageable pageable) {
        log.info("Listando funcionários: pagina={}, tamanho={}, ordenação={}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<FuncionarioDTO> page = funcionarioRepository.findAll(pageable).map(FuncionarioMapper::dto);
        log.info("Retornados {} funcionários (total de {} páginas)",
                page.getNumberOfElements(), page.getTotalPages());
        return page;
    }

}
