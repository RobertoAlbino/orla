package com.orla.teste.mapper;

import com.orla.teste.dto.ProjetoDTO;
import com.orla.teste.repository.entities.Projeto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProjetoMapper {

    public ProjetoDTO dto(Projeto projeto) {
        var funcionarios = projeto.getFuncionarios().stream().map(FuncionarioMapper::dto).collect(Collectors.toSet());
        return new ProjetoDTO(projeto.getId(), projeto.getNome(), projeto.getDataCriacao(), funcionarios);
    }

}
