package com.orla.teste.exceptions.handler;

import com.orla.teste.exceptions.dto.ApiErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleAll(Exception ex, WebRequest request) {
        String path = request.getDescription(false).substring(4);
        log.error("Erro na requisição {}: {}", path, ex.getMessage(), ex);
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var error = new ApiErrorDTO(
                Instant.now(),
                ex.getMessage(),
                path
        );
        log.info("Retornando resposta de erro {} para {}", status.value(), path);
        return ResponseEntity.status(status).body(error);
    }

}
