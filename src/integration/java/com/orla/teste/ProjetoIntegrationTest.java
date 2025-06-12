package com.orla.teste;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.flyway.clean-disabled=false", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class ProjetoIntegrationTest {

    private static final String JSON = """
                {"nome":"projeto","funcionarios": []}
                """;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("orla").withUsername("orla").withPassword("orla");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry reg) {
        reg.add("spring.datasource.url",    postgres::getJdbcUrl);
        reg.add("spring.datasource.username", postgres::getUsername);
        reg.add("spring.datasource.password", postgres::getPassword);
    }

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
        mockMvc.perform(MockMvcRequestBuilders.post("/projetos")
                        .contentType(MediaType.APPLICATION_JSON).content(JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        mockMvc.perform(MockMvcRequestBuilders.get("/projetos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deveFalharSemNomeInformado() throws Exception {
        var invalido = """
                {"nome":"","funcionarios": []}
                """;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/projetos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalido))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();

        String mensagem = result.getResponse().getContentAsString();
        assertTrue(mensagem.contains("Nome não informado"));
    }

    @Test
    void deveListar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/projetos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deveListarCodigo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/projetos")
                        .contentType(MediaType.APPLICATION_JSON).content(JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/projetos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
