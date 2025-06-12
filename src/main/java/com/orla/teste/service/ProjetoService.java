package com.orla.teste.service;

import com.orla.teste.dto.ProjetoCriacaoDTO;
import com.orla.teste.dto.ProjetoDTO;
import com.orla.teste.mapper.ProjetoMapper;
import com.orla.teste.repository.FuncionarioRepository;
import com.orla.teste.repository.ProjetoRepository;
import com.orla.teste.repository.entities.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;

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
        var funcionarios = new HashSet<>(funcionarioRepository.findAllById(dto.funcionarios()));
        Projeto entidade = new Projeto(null, dto.nome(), LocalDate.now(), funcionarios);
        entidade.validar();
        entidade = projetoRepository.save(entidade);
        return projetoMapper.dto(entidade);
    }

    public ProjetoDTO buscar(Long id) {
        return projetoRepository.findById(id).map(projeto -> projetoMapper.dto(projeto))
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Projeto não encontrado"));
    }

    public Page<ProjetoDTO> listar(Pageable pageable) {
        return projetoRepository.findAll(pageable).map(projeto -> projetoMapper.dto(projeto));
    }

}
