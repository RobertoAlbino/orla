package com.orla.teste.api;

import com.orla.teste.dto.FuncionarioCriacaoDTO;
import com.orla.teste.dto.FuncionarioDTO;
import com.orla.teste.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public FuncionarioDTO criar(@RequestBody FuncionarioCriacaoDTO dto) {
        return funcionarioService.criar(dto);
    }

    @GetMapping("/{id}")
    public FuncionarioDTO buscar(@PathVariable Long id) {
        return funcionarioService.buscar(id);
    }

    @GetMapping
    public Page<FuncionarioDTO> listar(Pageable pageable) {
        return funcionarioService.listar(pageable);
    }

}
