package com.orla.teste.repository.entities;

import br.com.caelum.stella.validation.CPFValidator;
import com.orla.teste.exceptions.CampoInvalidoException;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private BigDecimal salario;

    public void validar() {
        if (StringUtils.isBlank(nome)) {
            throw new CampoInvalidoException("Nome não informado");
        }
        new CPFValidator().assertValid(cpf);
        var emailValido = EmailValidator.getInstance().isValid(email);
        if (StringUtils.isBlank(email) || !emailValido) {
            throw new CampoInvalidoException("Email não informado ou inválido");
        }
        if (Objects.isNull(salario)) {
            throw new CampoInvalidoException("Salário não informado");
        }
    }

}
