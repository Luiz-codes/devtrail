# DevTrail

API para registrar e acompanhar a jornada de estudos de quem está aprendendo a programar: o que foi estudado, projetos, metas e horas dedicadas, com um dashboard de evolução.

> **Status:** em construção. A base do projeto (Spring Boot, banco na nuvem e migrations) está pronta, e as funcionalidades estão sendo desenvolvidas em etapas. Veja o [Roadmap](#roadmap).

## Sobre o projeto

O DevTrail nasceu como uma evolução do meu TCC no curso técnico de Desenvolvimento de Sistemas. Quis reconstruir a base (autenticação, perfis de acesso, multitenancy e dashboard) com outra stack e uma finalidade pessoal: ter um projeto robusto, bem estruturado e que eu possa olhar com orgulho.

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 4 (Web, Data JPA, Validation, Security) |
| Banco de dados | PostgreSQL (Neon, serverless) |
| Migrations | Flyway |
| Build | Maven |
| Utilitários | Lombok |
| Front-end (planejado) | Vue 3 |

## Decisões de arquitetura

- **Migrations versionadas com Flyway:** quem cria e altera as tabelas é o Flyway, e o Hibernate roda em modo `validate`, só conferindo se o código bate com o banco.
- **Nenhum segredo no repositório:** a conexão com o banco é lida de variáveis de ambiente.
- **Multitenancy por `workspace_id`:** cada espaço de estudo isola os dados dos seus membros.

## Como rodar localmente

### Pré-requisitos

- JDK 21
- Um banco PostgreSQL (por exemplo, um projeto gratuito no [Neon](https://neon.tech))

### 1. Clonar o repositório

```bash
git clone https://github.com/Luiz-codes/devtrail.git
cd devtrail
```

### 2. Configurar as variáveis de ambiente

| Variável | Descrição |
|---|---|
| `NEON_HOST` | Host do banco (sem `https://`, sem a porta) |
| `NEON_DB` | Nome do banco |
| `NEON_USER` | Usuário do banco |
| `NEON_PASSWORD` | Senha do banco |

No Windows (PowerShell), para as três primeiras:

```powershell
setx NEON_HOST "seu-host.neon.tech"
setx NEON_DB "neondb"
setx NEON_USER "seu-usuario"
```

A senha é melhor cadastrar pela tela **Editar as variáveis de ambiente da conta**, para não ficar no histórico do terminal. Abra um terminal novo depois.

No Linux ou macOS:

```bash
export NEON_HOST="seu-host.neon.tech"
export NEON_DB="neondb"
export NEON_USER="seu-usuario"
export NEON_PASSWORD="sua-senha"
```

### 3. Executar

```bash
./mvnw spring-boot:run        # Linux/macOS
.\mvnw.cmd spring-boot:run    # Windows
```

Na primeira execução, o Flyway cria as tabelas automaticamente. A API sobe em `http://localhost:8080`.

## Estrutura

```
src/main/java/com/luiz/devtrail    código da aplicação
src/main/resources/application.yml configuração (lê variáveis de ambiente)
src/main/resources/db/migration    migrations do Flyway (V1__, V2__, ...)
```

## Roadmap

- [x] Setup do projeto com Spring Boot, Java 21 e Maven
- [x] Conexão com PostgreSQL no Neon via variáveis de ambiente
- [x] Migrations com Flyway (tabela `workspace`)
- [ ] Autenticação com Spring Security e JWT
- [ ] Usuários e perfis de acesso
- [ ] Workspaces (multitenancy)
- [ ] Registro de estudos, projetos e metas
- [ ] Dashboard de evolução (horas por semana, sequência de dias)
- [ ] Testes automatizados
- [ ] Front-end em Vue 3
- [ ] Deploy

## Autor

**Luiz Henrique Moraes**, estudante de Desenvolvimento de Sistemas, com foco em desenvolvimento web full-stack.

- GitHub: [@Luiz-codes](https://github.com/Luiz-codes)
