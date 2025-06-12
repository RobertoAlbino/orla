package com.orla.teste.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FuncionarioDTO(Long id, String nome, String cpf, String email, BigDecimal salario) {}