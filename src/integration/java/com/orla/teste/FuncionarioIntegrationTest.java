package com.orla.teste;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.flyway.clean-disabled=false")
@AutoConfigureMockMvc
@Testcontainers
class FuncionarioIntegrationTest {

    private static final String JSON = """
                {"nome":"roberto","cpf": "89934813009", "email":"teste@gmail.com", "salario": "1000.50"}
                """;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("orla").withUsername("orla").withPassword("orla");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void resetarBanco() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void deveCriar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON).content(JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void deveFalharSemNomeInformado() throws Exception {
        var invalido = """
                {"nome":"","cpf": "89934813009", "email":"teste@gmail.com", "salario": "1000.50"}
                """;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalido))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();

        String mensagem = result.getResponse().getContentAsString();
        assertTrue(mensagem.contains("Nome não informado"));
    }

    @Test
    void deveFalharCpfInvalido() throws Exception {
        var invalido = """
                {"nome":"roberto","cpf": "89934813001", "email":"teste@gmail.com", "salario": "1000.50"}
                """;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalido))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();

        String mensagem = result.getResponse().getContentAsString();
        assertTrue(mensagem.contains("Validation errors: [CPFError : INVALID CHECK DIGITS]"));
    }

    @Test
    void deveFalharEmailInvalido() throws Exception {
        var invalido = """
                {"nome":"roberto","cpf": "89934813009", "email":"testegmail.com", "salario": "1000.50"}
                """;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalido))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();

        String mensagem = result.getResponse().getContentAsString();
        assertTrue(mensagem.contains("Email não informado ou inválido"));
    }

    @Test
    void deveFalharSemSalarioInformado() throws Exception {
        var invalido = """
                {"nome":"roberto","cpf": "89934813009", "email":"teste@gmail.com", "salario": ""}
                """;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalido))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();

        String mensagem = result.getResponse().getContentAsString();
        assertTrue(mensagem.contains("Salário não informado"));
    }

    @Test
    void deveListar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/funcionarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deveListarPorCodigo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON).content(JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/funcionarios/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
