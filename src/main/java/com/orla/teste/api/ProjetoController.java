package com.orla.teste.api;

import com.orla.teste.dto.ProjetoCriacaoDTO;
import com.orla.teste.dto.ProjetoDTO;
import com.orla.teste.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public ProjetoDTO criar(@RequestBody ProjetoCriacaoDTO dto) {
        return projetoService.criar(dto);
    }

    @GetMapping("/{id}")
    public ProjetoDTO buscar(@PathVariable Long id) {
        return projetoService.buscar(id);
    }

    @GetMapping
    public Page<ProjetoDTO> listar(Pageable pageable) {
        return projetoService.listar(pageable);
    }

}
