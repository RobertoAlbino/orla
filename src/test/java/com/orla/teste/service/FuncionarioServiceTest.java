package com.orla.teste.service;

import com.orla.teste.dto.FuncionarioCriacaoDTO;
import com.orla.teste.dto.FuncionarioDTO;
import com.orla.teste.mapper.FuncionarioMapper;
import com.orla.teste.repository.FuncionarioRepository;
import com.orla.teste.repository.entities.Funcionario;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService service;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar_deveSalvarERetornarDto() {
        FuncionarioCriacaoDTO dto = new FuncionarioCriacaoDTO("João", "18635610059", "joao@exemplo.com", new BigDecimal("5000"));
        Funcionario entidadeSalva = new Funcionario(1L, "João", "18635610059", "joao@exemplo.com", new BigDecimal("5000"));
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(entidadeSalva);
        FuncionarioDTO dtoRetornado = new FuncionarioDTO(1L, "João", "18635610059", "joao@exemplo.com", new BigDecimal("5000"));
        try (MockedStatic<FuncionarioMapper> mapper = mockStatic(FuncionarioMapper.class)) {
            mapper.when(() -> FuncionarioMapper.dto(entidadeSalva)).thenReturn(dtoRetornado);
            FuncionarioDTO resultado = service.criar(dto);
            assertSame(dtoRetornado, resultado);
            verify(funcionarioRepository).save(any(Funcionario.class));
            mapper.verify(() -> FuncionarioMapper.dto(entidadeSalva));
        }
    }

    @Test
    void buscar_quandoExistir_deveRetornarDto() {
        Funcionario entidade = new Funcionario(1L, "Maria", "18635610059", "maria@exemplo.com", new BigDecimal("6000"));
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(entidade));
        FuncionarioDTO dtoRetornado = new FuncionarioDTO(1L, "Maria", "98765432100", "maria@exemplo.com", new BigDecimal("6000"));
        try (MockedStatic<FuncionarioMapper> mapper = mockStatic(FuncionarioMapper.class)) {
            mapper.when(() -> FuncionarioMapper.dto(entidade)).thenReturn(dtoRetornado);
            FuncionarioDTO resultado = service.buscar(1L);
            assertSame(dtoRetornado, resultado);
            verify(funcionarioRepository).findById(1L);
            mapper.verify(() -> FuncionarioMapper.dto(entidade));
        }
    }

    @Test
    void buscar_quandoNaoExistir_deveLancarException() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.buscar(1L));
        assertEquals("Funcionario não encontrado", exception.getMessage());
    }

    @Test
    void listar_deveRetornarPaginaDeDto() {
        Funcionario entidade = new Funcionario(1L, "Pedro", "11122233344", "pedro@exemplo.com", new BigDecimal("7000"));
        Page<Funcionario> paginaEntidades = new PageImpl<>(List.of(entidade), PageRequest.of(0, 5), 1);
        when(funcionarioRepository.findAll(any(PageRequest.class))).thenReturn(paginaEntidades);
        FuncionarioDTO dto = new FuncionarioDTO(1L, "Pedro", "11122233344", "pedro@exemplo.com", new BigDecimal("7000"));
        try (MockedStatic<FuncionarioMapper> mapper = mockStatic(FuncionarioMapper.class)) {
            mapper.when(() -> FuncionarioMapper.dto(entidade)).thenReturn(dto);
            Page<FuncionarioDTO> resultado = service.listar(PageRequest.of(0, 5));
            assertEquals(1, resultado.getTotalElements());
            assertEquals(dto, resultado.getContent().get(0));
            verify(funcionarioRepository).findAll(any(PageRequest.class));
            mapper.verify(() -> FuncionarioMapper.dto(entidade));
        }
    }
}
