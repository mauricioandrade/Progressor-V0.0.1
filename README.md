<div align="center">

<img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/PostgreSQL-Docker-blue?style=for-the-badge&logo=postgresql&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Security-JWT-yellow?style=for-the-badge&logo=springsecurity&logoColor=white"/>
<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>

<br/><br/>

# 📈 Progressor

### 🇺🇸 English &nbsp;|&nbsp; 🇧🇷 [Português](#-progressor-1)

</div>

---

## 📌 About the Project

**Progressor** is a gym management system focused on **evolution**. Students and personal trainers can register, track measurements, manage training plans, and monitor physical progress over time.

> Optional trainer. Maximum autonomy. Every step tracked.

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 25 | Main language |
| Spring Boot | 4.0.3 | Application framework |
| Spring Web | — | REST API |
| Spring Data JPA | — | Database persistence |
| Spring Security | 7.x | Authentication and authorization |
| JJWT | 0.12.6 | JWT token generation and validation |
| Spring Actuator | — | Health check and monitoring |
| SpringDoc OpenAPI | — | Swagger documentation |
| PostgreSQL | 18 | Relational database |
| Docker | — | Local database container |
| Maven | — | Dependency management |

---

## 🏗️ Architecture

The project follows **Clean Architecture** organized into four layers:

```
dev.mauriciodev.Progressor_V001/
│
├── domain/                        → Business entities and rules
│   ├── person/                    → Person (base class)
│   ├── student/                   → Student entity + exceptions
│   ├── trainer/                   → PersonalTrainer entity + exceptions
│   ├── training/                  → TrainingPlan entity + exceptions
│   ├── shared/                    → Goal, TrainingLevel, Progressable
│   └── user/                      → User (auth entity), Role
│
├── application/                   → Use cases, services, DTOs, mappers
│   ├── auth/                      → AuthService, RegisterRequest, LoginRequest, AuthResponse
│   ├── student/                   → StudentService, StudentRequest/Response, StudentMapper
│   ├── trainer/                   → PersonalTrainerService, TrainerRequest/Response, TrainerMapper
│   └── training/                  → TrainingPlanService, TrainingPlanRequest/Response, TrainingPlanMapper
│
├── infrastructure/                → Frameworks and external integrations
│   ├── persistence/               → JPA Repositories
│   ├── security/                  → JWT filter, SecurityConfig, UserDetailsServiceImpl
│   └── openapi/                   → Swagger/OpenAPI configuration
│
└── presentation/                  → REST controllers and exception handlers
    ├── auth/                      → AuthController
    ├── student/                   → StudentController
    ├── trainer/                   → PersonalTrainerController
    ├── training/                  → TrainingPlanController
    ├── exception/                 → GlobalExceptionHandler
    └── UserController             → /api/users/me
```

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

**4. Access the API documentation**
```
http://localhost:8080/swagger-ui/index.html
```

**5. Check application health**
```
GET http://localhost:8080/actuator/health
```

---

## 🌐 API Endpoints

### Authentication
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/auth/register` | ❌ | Register a new user |
| `POST` | `/auth/login` | ❌ | Login and receive JWT token |

### Students
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/students` | ✅ | Register a new student |
| `GET` | `/students` | ✅ | List all students |
| `GET` | `/students/{id}` | ✅ | Find student by ID |
| `PATCH` | `/students/{id}/progress` | ✅ | Trigger student level progression |
| `GET` | `/students/{id}/history` | ✅ | Get training plan history |

### Personal Trainers
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/trainers` | ✅ | Register a new trainer |
| `GET` | `/trainers` | ✅ | List all trainers |
| `GET` | `/trainers/{id}` | ✅ | Find trainer by ID |

### Training Plans
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/training-plans` | ✅ | Create a new training plan |
| `GET` | `/training-plans` | ✅ | List all training plans |
| `GET` | `/training-plans/{id}` | ✅ | Find training plan by ID |
| `POST` | `/training-plans/{id}/assign/{studentId}` | ✅ | Assign plan to student |

### User Profile
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/users/me` | ✅ | Get authenticated user info |

### Monitoring
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/actuator/health` | ❌ | Application health check |

---

## 🗺️ Roadmap

- [x] 🏗️ **M1** — Project Foundation (setup, Docker, enums, package structure)
- [x] 🧱 **M2** — Core Entities (Person, Student, PersonalTrainer, TrainingPlan)
- [x] ⚙️ **M3** — Business Layer (repositories and services)
- [x] 🌐 **M4** — REST API (controllers and DTOs)
- [x] 📈 **M5** — Progression System (Progressable interface and evolution logic)
- [x] 🩺 **M6** — Health Check (Spring Actuator)
- [x] 📦 **M7** — Docker Complete (Dockerfile and full docker-compose)
- [x] 📖 **M8** — API Documentation (Swagger / OpenAPI)
- [x] 🔐 **M9** — Authentication (Spring Security + JWT)
- [x] ♻️ **M10** — Refactor: Clean Architecture Migration
- [ ] 🔗 **M11** — Auth: User–Domain Binding (User ↔ Student / Trainer)
- [ ] 🎓 **M12** — Student Module (self-service endpoints)
- [ ] 🏋️ **M13** — Personal Trainer Module (profile + student supervision)
- [ ] 📋 **M14** — Training Plans Module (role-based access + self-service)
- [ ] 📏 **M15** — Measurements Module (body tracking for students and trainers)
- [ ] 🎨 **M16** — Frontend (React + Vite)

---

## 🤝 How to Contribute

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

### 5. Create a branch
```bash
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

Open a **Pull Request** targeting the `main` branch.

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

**Progressor** é um sistema de gerenciamento de academia com foco em **evolução**. Alunos e personal trainers podem se cadastrar, registrar medidas, gerenciar planos de treino e acompanhar a evolução física ao longo do tempo.

> Personal opcional. Autonomia máxima. Cada etapa registrada.

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 25 | Linguagem principal |
| Spring Boot | 4.0.3 | Framework da aplicação |
| Spring Web | — | API REST |
| Spring Data JPA | — | Persistência de dados |
| Spring Security | 7.x | Autenticação e autorização |
| JJWT | 0.12.6 | Geração e validação de tokens JWT |
| Spring Actuator | — | Health check e monitoramento |
| SpringDoc OpenAPI | — | Documentação Swagger |
| PostgreSQL | 18 | Banco de dados relacional |
| Docker | — | Container do banco local |
| Maven | — | Gerenciamento de dependências |

---

## 🏗️ Arquitetura

O projeto segue **Clean Architecture** organizada em quatro camadas:

```
dev.mauriciodev.Progressor_V001/
│
├── domain/                        → Entidades e regras de negócio
│   ├── person/                    → Person (classe base)
│   ├── student/                   → Entidade Student + exceções
│   ├── trainer/                   → Entidade PersonalTrainer + exceções
│   ├── training/                  → Entidade TrainingPlan + exceções
│   ├── shared/                    → Goal, TrainingLevel, Progressable
│   └── user/                      → User (entidade de auth), Role
│
├── application/                   → Casos de uso, services, DTOs, mappers
│   ├── auth/                      → AuthService, RegisterRequest, LoginRequest, AuthResponse
│   ├── student/                   → StudentService, StudentRequest/Response, StudentMapper
│   ├── trainer/                   → PersonalTrainerService, TrainerRequest/Response, TrainerMapper
│   └── training/                  → TrainingPlanService, TrainingPlanRequest/Response, TrainingPlanMapper
│
├── infrastructure/                → Frameworks e integrações externas
│   ├── persistence/               → Repositórios JPA
│   ├── security/                  → Filtro JWT, SecurityConfig, UserDetailsServiceImpl
│   └── openapi/                   → Configuração Swagger/OpenAPI
│
└── presentation/                  → Controllers REST e tratamento de exceções
    ├── auth/                      → AuthController
    ├── student/                   → StudentController
    ├── trainer/                   → PersonalTrainerController
    ├── training/                  → TrainingPlanController
    ├── exception/                 → GlobalExceptionHandler
    └── UserController             → /api/users/me
```

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

**4. Acesse a documentação da API**
```
http://localhost:8080/swagger-ui/index.html
```

**5. Verifique a saúde da aplicação**
```
GET http://localhost:8080/actuator/health
```

---

## 🌐 Endpoints da API

### Autenticação
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `POST` | `/auth/register` | ❌ | Registrar novo usuário |
| `POST` | `/auth/login` | ❌ | Login e recebimento do token JWT |

### Alunos
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `POST` | `/students` | ✅ | Cadastrar novo aluno |
| `GET` | `/students` | ✅ | Listar todos os alunos |
| `GET` | `/students/{id}` | ✅ | Buscar aluno por ID |
| `PATCH` | `/students/{id}/progress` | ✅ | Disparar progressão do aluno |
| `GET` | `/students/{id}/history` | ✅ | Histórico de planos de treino |

### Personal Trainers
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `POST` | `/trainers` | ✅ | Cadastrar novo personal |
| `GET` | `/trainers` | ✅ | Listar todos os personais |
| `GET` | `/trainers/{id}` | ✅ | Buscar personal por ID |

### Planos de Treino
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `POST` | `/training-plans` | ✅ | Criar novo plano de treino |
| `GET` | `/training-plans` | ✅ | Listar todos os planos |
| `GET` | `/training-plans/{id}` | ✅ | Buscar plano por ID |
| `POST` | `/training-plans/{id}/assign/{studentId}` | ✅ | Atribuir plano ao aluno |

### Perfil do Usuário
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `GET` | `/api/users/me` | ✅ | Retornar dados do usuário autenticado |

### Monitoramento
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `GET` | `/actuator/health` | ❌ | Health check da aplicação |

---

## 🗺️ Roadmap

- [x] 🏗️ **M1** — Project Foundation (setup, Docker, enums, estrutura de pacotes)
- [x] 🧱 **M2** — Core Entities (Person, Student, PersonalTrainer, TrainingPlan)
- [x] ⚙️ **M3** — Business Layer (repositórios e services)
- [x] 🌐 **M4** — REST API (controllers e DTOs)
- [x] 📈 **M5** — Progression System (interface Progressable e lógica de evolução)
- [x] 🩺 **M6** — Health Check (Spring Actuator)
- [x] 📦 **M7** — Docker Complete (Dockerfile e docker-compose completo)
- [x] 📖 **M8** — API Documentation (Swagger / OpenAPI)
- [x] 🔐 **M9** — Authentication (Spring Security + JWT)
- [x] ♻️ **M10** — Refactor: Clean Architecture Migration
- [ ] 🔗 **M11** — Auth: User–Domain Binding (User ↔ Student / Trainer)
- [ ] 🎓 **M12** — Student Module (endpoints de auto-serviço)
- [ ] 🏋️ **M13** — Personal Trainer Module (perfil + supervisão de alunos)
- [ ] 📋 **M14** — Training Plans Module (controle por role + auto-serviço)
- [ ] 📏 **M15** — Measurements Module (registro de medidas corporais)
- [ ] 🎨 **M16** — Frontend (React + Vite)

---

## 🤝 Como Contribuir

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

### 5. Crie uma branch
```bash
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

Abra um **Pull Request** apontando para a branch `main`.

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
  <sub>Built with 💪 by <a href="https://github.com/mauricioandrade">mauricioandrade</a></sub>
</div>
