package com.orla.teste.api;

import com.orla.teste.dto.ProjetoCriacaoDTO;
import com.orla.teste.dto.ProjetoDTO;
import com.orla.teste.service.ProjetoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public ProjetoDTO criar(@RequestBody ProjetoCriacaoDTO dto) {
        log.info("Solicitação de criação de projeto recebida: {}", dto);
        ProjetoDTO criado = projetoService.criar(dto);
        log.info("Projeto criado com sucesso: {}", criado);
        return criado;
    }

    @GetMapping("/{id}")
    public ProjetoDTO buscar(@PathVariable Long id) {
        log.info("Solicitação de busca de projeto com ID: {}", id);
        ProjetoDTO projeto = projetoService.buscar(id);
        log.info("Projeto encontrado: {}", projeto);
        return projeto;
    }

    @GetMapping
    public Page<ProjetoDTO> listar(Pageable pageable) {
        log.info("Solicitação de listagem de projetos: pagina={}, tamanho={}, ordenação={}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<ProjetoDTO> page = projetoService.listar(pageable);
        log.info("Retornando {} projetos (total de {} páginas)",
                page.getNumberOfElements(), page.getTotalPages());
        return page;
    }
}
