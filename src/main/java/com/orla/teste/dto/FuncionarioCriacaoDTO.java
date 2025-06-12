package com.orla.teste.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FuncionarioCriacaoDTO(String nome, String cpf, String email, BigDecimal salario) {}
