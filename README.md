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

## Dependências do projeto
* Docker
---

## Executando com Docker

```bash
$ docker compose up
```
---

## Testes de Integração com Test Containers
Os testes de integração utilizam um postgress real usando test containers.

---

## Logs
Todos os pontos importantes da aplicação possuem cobertura de logs

---

## Documentação com OPEN API
A documentação dos endpoints se encontra em: http://localhost:8080/swagger-ui/index.html#/

---

## Integração Contínua (GitHub Actions)
Este repositório possui um workflow **CI** em `.github/workflows/ci.yml` que executa automaticamente as suites de teste do projeto para a branch `master`.





