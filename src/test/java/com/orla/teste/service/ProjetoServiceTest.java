package com.orla.teste.service;

import com.orla.teste.dto.ProjetoCriacaoDTO;
import com.orla.teste.dto.ProjetoDTO;
import com.orla.teste.mapper.ProjetoMapper;
import com.orla.teste.repository.FuncionarioRepository;
import com.orla.teste.repository.ProjetoRepository;
import com.orla.teste.repository.entities.Projeto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProjetoServiceTest {

    @InjectMocks
    private ProjetoService service;

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private ProjetoMapper projetoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar_deveRetornarDto() {
        ProjetoCriacaoDTO dto = new ProjetoCriacaoDTO("API", Set.of(1L, 2L));
        when(funcionarioRepository.findAllById(dto.funcionarios())).thenReturn(Collections.emptyList());
        Projeto entidadeSalva = new Projeto(1L, "API", LocalDate.now(), Collections.emptySet());
        when(projetoRepository.save(any(Projeto.class))).thenReturn(entidadeSalva);
        ProjetoDTO dtoRetornado = new ProjetoDTO(1L, "API", entidadeSalva.getDataCriacao(), Set.of());
        when(projetoMapper.dto(entidadeSalva)).thenReturn(dtoRetornado);

        ProjetoDTO resultado = service.criar(dto);

        assertSame(dtoRetornado, resultado);
        verify(funcionarioRepository).findAllById(dto.funcionarios());
        verify(projetoRepository).save(any(Projeto.class));
        verify(projetoMapper).dto(entidadeSalva);
    }

    @Test
    void buscar_quandoExistir_deveRetornarDto() {
        Projeto entidade = new Projeto(1L, "API", LocalDate.of(2025, 6, 11), Collections.emptySet());
        when(projetoRepository.findById(1L)).thenReturn(java.util.Optional.of(entidade));
        ProjetoDTO dtoRetornado = new ProjetoDTO(1L, "API", entidade.getDataCriacao(), Set.of());
        when(projetoMapper.dto(entidade)).thenReturn(dtoRetornado);

        ProjetoDTO resultado = service.buscar(1L);

        assertSame(dtoRetornado, resultado);
        verify(projetoRepository).findById(1L);
        verify(projetoMapper).dto(entidade);
    }

    @Test
    void buscar_quandoNaoExistir_deveLancarException() {
        when(projetoRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> service.buscar(1L)
        );
        assertEquals("Projeto não encontrado", exception.getMessage());
    }

    @Test
    void listar_deveRetornarPaginaDeDto() {
        Projeto entidade = new Projeto(1L, "API", LocalDate.of(2025, 6, 11), Collections.emptySet());
        Page<Projeto> paginaEntidades = new PageImpl<>(List.of(entidade), PageRequest.of(0, 10), 1);
        when(projetoRepository.findAll(any(PageRequest.class))).thenReturn(paginaEntidades);
        ProjetoDTO dto = new ProjetoDTO(1L, "API", entidade.getDataCriacao(), Set.of());
        when(projetoMapper.dto(entidade)).thenReturn(dto);

        Page<ProjetoDTO> resultado = service.listar(PageRequest.of(0, 10));

        assertEquals(1, resultado.getTotalElements());
        assertEquals(dto, resultado.getContent().get(0));
        verify(projetoRepository).findAll(any(PageRequest.class));
        verify(projetoMapper).dto(entidade);
    }
}
