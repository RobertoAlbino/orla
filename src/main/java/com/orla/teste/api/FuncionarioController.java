package com.orla.teste.api;

import com.orla.teste.dto.FuncionarioCriacaoDTO;
import com.orla.teste.dto.FuncionarioDTO;
import com.orla.teste.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public FuncionarioDTO criar(@RequestBody FuncionarioCriacaoDTO dto) {
        log.info("POST /funcionarios dto={}", dto);
        FuncionarioDTO retorno = funcionarioService.criar(dto);
        log.info("POST /funcionarios criado id={}", retorno.id());
        return retorno;
    }

    @GetMapping("/{id}")
    public FuncionarioDTO buscar(@PathVariable Long id) {
        log.info("GET /funcionarios/{}", id);
        FuncionarioDTO retorno = funcionarioService.buscar(id);
        log.info("GET /funcionarios/{} encontrado", id);
        return retorno;
    }

    @GetMapping
    public Page<FuncionarioDTO> listar(Pageable pageable) {
        log.info("GET /funcionarios page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        Page<FuncionarioDTO> pagina = funcionarioService.listar(pageable);
        log.info("GET /funcionarios total={}", pagina.getTotalElements());
        return pagina;
    }
}
