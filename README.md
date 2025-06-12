# Orla

## 📂 Estrutura do Projeto

```
Orla/
 ├── src/                          # Código Fonte
    ├── main/                      # Código da aplicação
    ├── integration/               # Testes de integração
    ├── test/                      # Testes Unitários
 ├── pom.xml            # dependências Maven
 ├── Dockerfile         # build + runtime em containers
 ├── docker-compose.yml # orquestração para app + banco de dados + testes
```

---
## Rodando com aplicação + database com Docker

```bash
$ docker compose up
```
---

## Testes de Integração com Test Containers
Os testes de integração utilizam um postgress real usando test containers.

---

## Documentação com OPEN API
A documentação dos endpoints se encontra em: http://localhost:8080/swagger-ui/index.html#/

---

## Integração Contínua (GitHub Actions)
Este repositório possui um workflow **CI** em `.github/workflows/ci.yml` que executa automaticamente a suíte de testes JUnit 5 em cada push request para a branch `master`.





