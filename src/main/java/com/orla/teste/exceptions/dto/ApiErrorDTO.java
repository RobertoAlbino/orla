package com.orla.teste.exceptions.dto;

import java.time.Instant;

public record ApiErrorDTO(
        Instant timestamp,
        String message,
        String path) {}