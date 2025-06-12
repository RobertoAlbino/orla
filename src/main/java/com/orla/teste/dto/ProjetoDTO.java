package com.orla.teste.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record ProjetoDTO(Long id, String nome, LocalDate dataCriacao, Set<FuncionarioDTO> funcionarios) {}

