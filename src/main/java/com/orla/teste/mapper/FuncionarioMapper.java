package com.orla.teste.mapper;

import com.orla.teste.dto.FuncionarioDTO;
import com.orla.teste.repository.entities.Funcionario;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {

    public static FuncionarioDTO dto(Funcionario funcionario) {
        return FuncionarioDTO
                .builder()
                .id(funcionario.getId())
                .nome(funcionario.getNome())
                .cpf(funcionario.getCpf())
                .email(funcionario.getEmail())
                .salario(funcionario.getSalario())
                .build();
    }

}
