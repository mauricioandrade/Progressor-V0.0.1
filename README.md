<div align="center">

<img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/PostgreSQL-Docker-blue?style=for-the-badge&logo=postgresql&logoColor=white"/>
<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
<img src="https://img.shields.io/badge/Actuator-enabled-brightgreen?style=for-the-badge&logo=springboot&logoColor=white"/>

<br/><br/>

# 📈 Progressor

### 🇺🇸 English &nbsp;|&nbsp; 🇧🇷 [Português](#-progressor-1)

</div>

---

## 📌 About the Project

**Progressor** is a gym student management system focused on **evolution**. The platform tracks students, personal trainers, and training plans — automatically evolving each student's level and assigned plan as they progress over time.

> The system grows with the student. From beginner to advanced, every step is tracked.

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 25 | Main language |
| Spring Boot | 4.0.3 | Application framework |
| Spring Web | — | REST API |
| Spring Data JPA | — | Database persistence |
| Spring Actuator | — | Health check and monitoring |
| PostgreSQL | 18 | Relational database |
| Docker | — | Local database container |
| Maven | — | Dependency management |

---

## 🚀 How to Run Locally

### Prerequisites
- Java 25+
- Maven
- Docker Desktop

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/mauricioandrade/Progressor-V0.0.1.git
cd Progressor-V0.0.1
```

**2. Start the PostgreSQL container**
```bash
docker compose up -d
```

**3. Run the application**
```bash
mvn spring-boot:run
```

**4. Access the API**
```
http://localhost:8080
```

**5. Check application health**
```
GET http://localhost:8080/actuator/health
```

---

## 🌐 API Endpoints

### Students
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/students` | Register a new student |
| `GET` | `/students` | List all students |
| `GET` | `/students/{id}` | Find student by ID |
| `PATCH` | `/students/{id}/progress` | Trigger student progression |
| `GET` | `/students/{id}/history` | Get training plan history |

### Personal Trainers
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/trainers` | Register a new trainer |
| `GET` | `/trainers` | List all trainers |
| `GET` | `/trainers/{id}` | Find trainer by ID |

### Training Plans
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/training-plans` | Create a new plan |
| `GET` | `/training-plans` | List all plans |
| `POST` | `/training-plans/{id}/assign/{studentId}` | Assign plan to student |

### Monitoring
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/actuator/health` | Application health check |
| `GET` | `/actuator/info` | Application info |

---

## 🗂️ Package Structure

```
com.progressor
├── controller        → REST endpoints
├── service           → Business logic
├── repository        → Database access (JPA)
└── domain
    ├── entity        → JPA entities (Person, Student, PersonalTrainer, TrainingPlan)
    ├── enums         → TrainingLevel, Goal
    └── interfaces    → Progressable
```

---

## 🗺️ Roadmap

- [x] 🏗️ **M1** — Project Foundation (setup, Docker, enums, package structure)
- [x] 🧱 **M2** — Core Entities (Person, Student, PersonalTrainer, TrainingPlan)
- [x] ⚙️ **M3** — Business Layer (repositories and services)
- [x] 🌐 **M4** — REST API (controllers and DTOs)
- [x] 📈 **M5** — Progression System (Progressable interface and evolution logic)
- [x] 🩺 **M6** — Health Check (Spring Actuator)
- [ ] 📦 **M7** — Docker Complete (Dockerfile and full docker-compose)
- [ ] 📖 **M8** — API Documentation (Swagger / OpenAPI)
- [ ] 🔐 **M9** — Authentication (Spring Security)

---

## 🤝 How to Contribute

We welcome contributions! Follow the steps below to avoid conflicts and keep things organized.

### 1. Fork the repository
Click the **Fork** button at the top right of this page.

### 2. Clone your fork
```bash
git clone https://github.com/YOUR_USERNAME/Progressor-V0.0.1.git
cd Progressor-V0.0.1
```

### 3. Set up the upstream remote
```bash
git remote add upstream https://github.com/mauricioandrade/Progressor-V0.0.1.git
```

### 4. Pick an issue
- Go to the [Issues](https://github.com/mauricioandrade/Progressor-V0.0.1/issues) tab
- Pick an open issue that interests you
- Comment: **"I'd like to work on this"**
- Wait for it to be assigned to you

### 5. Create a branch from develop
```bash
git checkout develop
git pull upstream develop
git checkout -b feature/issue-XX-short-description
```

### 6. Commit your changes
```bash
git commit -m "feat(scope): short description"
```

### 7. Push and open a Pull Request
```bash
git push origin feature/issue-XX-short-description
```

Open a **Pull Request** targeting the `develop` branch — **not** `main`.

### 8. Stay in sync to avoid conflicts
Before opening your PR, always sync with upstream:
```bash
git fetch upstream
git rebase upstream/develop
```

Resolve any conflicts locally before pushing.

---

## 📋 Commit Convention

```
<type>(<scope>): <short message>
```

| Type | When to use |
|---|---|
| `feat` | New feature |
| `fix` | Bug fix |
| `chore` | Setup, config, dependencies |
| `refactor` | Code improvement without behavior change |
| `docs` | Documentation |
| `test` | Tests |

---

<br/><br/>

---

<div align="center">

# 📈 Progressor

### 🇧🇷 Português &nbsp;|&nbsp; 🇺🇸 [English](#-progressor)

</div>

---

## 📌 Sobre o Projeto

**Progressor** é um sistema de gerenciamento de alunos de academia com foco em **evolução**. A plataforma acompanha alunos, personal trainers e planos de treino — evoluindo automaticamente o nível e o plano de cada aluno conforme ele progride ao longo do tempo.

> O sistema cresce junto com o aluno. Do iniciante ao avançado, cada etapa é registrada.

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 25 | Linguagem principal |
| Spring Boot | 4.0.3 | Framework da aplicação |
| Spring Web | — | API REST |
| Spring Data JPA | — | Persistência de dados |
| Spring Actuator | — | Health check e monitoramento |
| PostgreSQL | 18 | Banco de dados relacional |
| Docker | — | Container do banco local |
| Maven | — | Gerenciamento de dependências |

---

## 🚀 Como Rodar Localmente

### Pré-requisitos
- Java 25+
- Maven
- Docker Desktop

### Passo a passo

**1. Clone o repositório**
```bash
git clone https://github.com/mauricioandrade/Progressor-V0.0.1.git
cd Progressor-V0.0.1
```

**2. Suba o container do PostgreSQL**
```bash
docker compose up -d
```

**3. Rode a aplicação**
```bash
mvn spring-boot:run
```

**4. Acesse a API**
```
http://localhost:8080
```

**5. Verifique a saúde da aplicação**
```
GET http://localhost:8080/actuator/health
```

---

## 🌐 Endpoints da API

### Alunos
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/students` | Cadastrar novo aluno |
| `GET` | `/students` | Listar todos os alunos |
| `GET` | `/students/{id}` | Buscar aluno por ID |
| `PATCH` | `/students/{id}/progress` | Disparar progressão do aluno |
| `GET` | `/students/{id}/history` | Histórico de planos de treino |

### Personal Trainers
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/trainers` | Cadastrar novo personal |
| `GET` | `/trainers` | Listar todos os personais |
| `GET` | `/trainers/{id}` | Buscar personal por ID |

### Planos de Treino
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/training-plans` | Criar novo plano |
| `GET` | `/training-plans` | Listar todos os planos |
| `POST` | `/training-plans/{id}/assign/{studentId}` | Atribuir plano ao aluno |

### Monitoramento
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/actuator/health` | Health check da aplicação |
| `GET` | `/actuator/info` | Informações da aplicação |

---

## 🗂️ Estrutura de Pacotes

```
com.progressor
├── controller        → Endpoints REST
├── service           → Regras de negócio
├── repository        → Acesso ao banco (JPA)
└── domain
    ├── entity        → Entidades JPA (Person, Student, PersonalTrainer, TrainingPlan)
    ├── enums         → TrainingLevel, Goal
    └── interfaces    → Progressable
```

---

## 🗺️ Roadmap

- [x] 🏗️ **M1** — Project Foundation (setup, Docker, enums, estrutura de pacotes)
- [x] 🧱 **M2** — Core Entities (Person, Student, PersonalTrainer, TrainingPlan)
- [x] ⚙️ **M3** — Business Layer (repositórios e services)
- [x] 🌐 **M4** — REST API (controllers e DTOs)
- [x] 📈 **M5** — Progression System (interface Progressable e lógica de evolução)
- [x] 🩺 **M6** — Health Check (Spring Actuator)
- [ ] 📦 **M7** — Docker Complete (Dockerfile e docker-compose completo)
- [ ] 📖 **M8** — API Documentation (Swagger / OpenAPI)
- [ ] 🔐 **M9** — Authentication (Spring Security)

---

## 🤝 Como Contribuir

Contribuições são bem-vindas! Siga os passos abaixo para evitar conflitos e manter o projeto organizado.

### 1. Faça um Fork do repositório
Clique no botão **Fork** no canto superior direito desta página.

### 2. Clone o seu fork
```bash
git clone https://github.com/SEU_USUARIO/Progressor-V0.0.1.git
cd Progressor-V0.0.1
```

### 3. Configure o remote upstream
```bash
git remote add upstream https://github.com/mauricioandrade/Progressor-V0.0.1.git
```

### 4. Escolha uma issue
- Acesse a aba [Issues](https://github.com/mauricioandrade/Progressor-V0.0.1/issues)
- Escolha uma issue aberta que te interesse
- Comente: **"Gostaria de trabalhar nessa issue"**
- Aguarde ser atribuída a você

### 5. Crie uma branch a partir da develop
```bash
git checkout develop
git pull upstream develop
git checkout -b feature/issue-XX-descricao-curta
```

### 6. Faça seus commits
```bash
git commit -m "feat(escopo): descrição curta em inglês"
```

### 7. Suba e abra um Pull Request
```bash
git push origin feature/issue-XX-descricao-curta
```

Abra um **Pull Request** apontando para a branch `develop` — **não** para a `main`.

### 8. Evite conflitos
Antes de abrir o PR, sincronize com o upstream:
```bash
git fetch upstream
git rebase upstream/develop
```

Resolva qualquer conflito localmente antes de subir.

---

## 📋 Convenção de Commits

```
<tipo>(<escopo>): <mensagem curta em inglês>
```

| Tipo | Quando usar |
|---|---|
| `feat` | Nova funcionalidade |
| `fix` | Correção de bug |
| `chore` | Setup, config, dependências |
| `refactor` | Melhoria sem mudar comportamento |
| `docs` | Documentação |
| `test` | Testes |

---

<div align="center">
  <sub>Built with 💪 by <a href="https://github.com/mauricioandrade">mauricioandrade</a> and contributors</sub>
</div>
