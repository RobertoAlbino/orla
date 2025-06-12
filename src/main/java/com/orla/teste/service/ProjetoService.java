package com.orla.teste.service;

import com.orla.teste.dto.ProjetoCriacaoDTO;
import com.orla.teste.dto.ProjetoDTO;
import com.orla.teste.mapper.ProjetoMapper;
import com.orla.teste.repository.FuncionarioRepository;
import com.orla.teste.repository.ProjetoRepository;
import com.orla.teste.repository.entities.Projeto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ProjetoMapper projetoMapper;

    @Transactional
    public ProjetoDTO criar(ProjetoCriacaoDTO dto) {
        log.info("Iniciando criação de projeto: {}", dto);
        Set<?> funcionarios = new HashSet<>(funcionarioRepository.findAllById(dto.funcionarios()));
        log.debug("Funcionários carregados para o projeto {}: {}", dto.nome(), funcionarios);
        Projeto entidade = new Projeto(null, dto.nome(), LocalDate.now(), (Set) funcionarios);
        entidade.validar();
        entidade = projetoRepository.save(entidade);
        ProjetoDTO resultado = projetoMapper.dto(entidade);
        log.info("Projeto criado com sucesso: {}", resultado);
        return resultado;
    }

    public ProjetoDTO buscar(Long id) {
        log.info("Buscando projeto com ID: {}", id);
        ProjetoDTO dto = projetoRepository.findById(id)
                .map(projetoMapper::dto)
                .orElseThrow(() -> {
                    log.error("Projeto não encontrado: ID {}", id);
                    return new EntityNotFoundException("Projeto não encontrado");
                });
        log.info("Projeto encontrado: {}", dto);
        return dto;
    }

    public Page<ProjetoDTO> listar(Pageable pageable) {
        log.info("Listando projetos: pagina={}, tamanho={}, ordenação={}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<ProjetoDTO> page = projetoRepository.findAll(pageable).map(projetoMapper::dto);
        log.info("Retornados {} projetos (total de {} páginas)",
                page.getNumberOfElements(), page.getTotalPages());
        return page;
    }

}
