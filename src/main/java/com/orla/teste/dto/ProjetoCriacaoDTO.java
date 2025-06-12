package com.orla.teste.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record ProjetoCriacaoDTO(String nome, Set<Long> funcionarios) {}