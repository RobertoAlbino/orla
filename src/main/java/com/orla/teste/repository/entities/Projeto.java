package com.orla.teste.repository.entities;

import br.com.caelum.stella.validation.CPFValidator;
import com.orla.teste.exceptions.CampoInvalidoException;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projeto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataCriacao;

    @ManyToMany
    @JoinTable(name = "projeto_funcionario",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "funcionario_id"))
    private Set<Funcionario> funcionarios;


    public void validar() {
        if (StringUtils.isBlank(nome)) {
            throw new CampoInvalidoException("Nome não informado");
        }
        if (Objects.isNull(dataCriacao)) {
            throw new CampoInvalidoException("Data Criação não informada");
        }
    }

}
