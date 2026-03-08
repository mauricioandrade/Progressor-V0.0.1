<div align="center">

<img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/PostgreSQL-Docker-blue?style=for-the-badge&logo=postgresql&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Security-JWT-yellow?style=for-the-badge&logo=springsecurity&logoColor=white"/>
<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>

<br/><br/>

# 📈 Progressor V0.0.1

**Backend API · [Frontend →](https://github.com/mauricioandrade/progressor-frontend)**

### 🇺🇸 English &nbsp;|&nbsp; 🇧🇷 [Português](#-progressor-v001-1)

</div>

---

## 📌 About the Project

**Progressor** is a gym management system focused on **evolution**. Students and personal trainers can register, track measurements, manage training plans, and monitor physical progress over time.

> Optional trainer. Maximum autonomy. Every step tracked.

**V0.0.1 is complete** — all 16 milestones implemented, from project setup to a fully functional React frontend.

🎨 **Frontend:** [github.com/mauricioandrade/progressor-frontend](https://github.com/mauricioandrade/progressor-frontend)

---

## 🛠️ Tech Stack

### Backend
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

## 🏗️ Architecture — Clean Architecture

```
dev.mauriciodev.Progressor_V001/
│
├── domain/                        → Business entities and rules
│   ├── person/                    → Person (base class)
│   ├── student/                   → Student entity + exceptions
│   ├── trainer/                   → PersonalTrainer entity + exceptions
│   ├── training/                  → TrainingPlan entity + exceptions
│   ├── measurement/               → Measurement entity + exceptions
│   ├── shared/                    → Goal, TrainingLevel, Progressable
│   └── user/                      → User (auth entity), Role
│
├── application/                   → Use cases, services, DTOs, mappers
│   ├── auth/                      → AuthService, RegisterRequest, LoginRequest, AuthResponse
│   ├── student/                   → StudentService, StudentRequest/Response, StudentMapper
│   ├── trainer/                   → PersonalTrainerService, TrainerRequest/Response, TrainerMapper
│   ├── training/                  → TrainingPlanService, TrainingPlanRequest/Response, TrainingPlanMapper
│   └── measurement/               → MeasurementService, MeasurementRequest/Response, MeasurementMapper
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
    ├── measurement/               → MeasurementController
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

> The backend runs on **port 8081** (`server.port=8081` in `application.yml`).

---

## 📖 Testing the API

### Option 1 — Swagger UI (browser)

Access the interactive API documentation:
```
http://localhost:8081/swagger-ui/index.html
```

**How to authenticate in Swagger:**
1. Call `POST /auth/login` with your credentials
2. Copy the `token` from the response
3. Click the **Authorize 🔓** button at the top right
4. Paste: `Bearer <your_token>`
5. Click **Authorize** — all subsequent requests will include the token

**Suggested test flow in Swagger:**
```
POST /auth/register    → create a STUDENT account
POST /auth/register    → create a TRAINER account
POST /auth/login       → login as STUDENT, copy token, authorize
GET  /students/me      → view student profile
PUT  /students/me      → update age, weight, height, goal
POST /auth/login       → login as TRAINER, copy token, authorize
POST /trainers/me/students/{id}  → link student to trainer
POST /training-plans   → create a training plan for the student
POST /auth/login       → login as STUDENT again
GET  /training-plans/me/current  → view current plan
POST /measurements     → record a measurement
POST /measurements     → record a second measurement
GET  /measurements/evolution     → view evolution deltas
```

---

### Option 2 — Postman Collection

Import the collection file from `docs/Progressor-API.postman_collection.json`.

**Features:**
- Pre-configured base URL (`http://localhost:8081`)
- Login requests automatically save `{{studentToken}}` and `{{trainerToken}}` as collection variables — no manual copy/paste needed
- All endpoints organized by module: Auth, Students, Trainers, Training Plans, Measurements

**Quick start:**
1. Import the collection in Postman: **Import → Upload Files**
2. Run **Login Student** → token saved automatically
3. Run **Login Trainer** → token saved automatically
4. All other requests use `{{studentToken}}` or `{{trainerToken}}` automatically

---

## 🌐 API Endpoints

### Authentication
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/auth/register` | ❌ | Register a new user (STUDENT or TRAINER) |
| `POST` | `/auth/login` | ❌ | Login and receive JWT token |
| `GET` | `/api/users/me` | ✅ | Get authenticated user info |

### Students
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/students/me` | ✅ STUDENT | Get own profile |
| `PUT` | `/students/me` | ✅ STUDENT | Update own profile |
| `PATCH` | `/students/me/progress` | ✅ STUDENT | Trigger level progression |

### Personal Trainers
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/trainers/me` | ✅ TRAINER | Get own profile |
| `POST` | `/trainers/me/students/{id}` | ✅ TRAINER | Link a student |
| `DELETE` | `/trainers/me/students/{id}` | ✅ TRAINER | Unlink a student |
| `GET` | `/trainers/me/students` | ✅ TRAINER | List supervised students |

### Training Plans
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/training-plans` | ✅ TRAINER | Create plan for a student |
| `POST` | `/training-plans/me` | ✅ STUDENT | Create own training plan |
| `GET` | `/training-plans/me/current` | ✅ STUDENT | Get current active plan |
| `GET` | `/training-plans/me/history` | ✅ STUDENT | Get plan history |
| `GET` | `/training-plans` | ✅ | List all training plans |

### Measurements
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/measurements` | ✅ STUDENT | Record a new measurement |
| `GET` | `/measurements` | ✅ STUDENT | List all measurements |
| `GET` | `/measurements/evolution` | ✅ STUDENT | Get evolution deltas |
| `GET` | `/measurements/trainer/students/{id}` | ✅ TRAINER | View student measurements |

**Measurement fields:**
`weightKg`, `heightCm`, `bodyFatPercent`, `muscleMassPercent`, `waistCm`, `hipCm`, `chestCm`, `rightArmCm`, `leftArmCm`, `rightThighCm`, `leftThighCm`

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
- [x] 🔗 **M11** — Auth: User–Domain Binding (User ↔ Student / Trainer)
- [x] 🎓 **M12** — Student Module (self-service endpoints)
- [x] 🏋️ **M13** — Personal Trainer Module (profile + student supervision)
- [x] 📋 **M14** — Training Plans Module (role-based access + self-service)
- [x] 📏 **M15** — Measurements Module (body tracking: weight, body fat, arms D/E, thighs D/E...)
- [x] 🎨 **M16** — Frontend (React + Vite + TypeScript + Tailwind)

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
- Comment: **"I'd like to work on this"** and wait for assignment

### 5. Create a branch, commit and open a Pull Request
```bash
git checkout -b feature/issue-XX-short-description
git commit -m "feat(scope): short description"
git push origin feature/issue-XX-short-description
```

---

## 📋 Commit Convention

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

# 📈 Progressor V0.0.1

**Backend API · [Frontend →](https://github.com/mauricioandrade/progressor-frontend)**

### 🇧🇷 Português &nbsp;|&nbsp; 🇺🇸 [English](#-progressor-v001)

</div>

---

## 📌 Sobre o Projeto

**Progressor** é um sistema de gerenciamento de academia com foco em **evolução**. Alunos e personal trainers podem se cadastrar, registrar medidas, gerenciar planos de treino e acompanhar a evolução física ao longo do tempo.

> Personal opcional. Autonomia máxima. Cada etapa registrada.

**V0.0.1 está completa** — todos os 16 milestones implementados, do setup inicial até um frontend React totalmente funcional.

🎨 **Frontend:** [github.com/mauricioandrade/progressor-frontend](https://github.com/mauricioandrade/progressor-frontend)

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

## 🏗️ Arquitetura — Clean Architecture

```
dev.mauriciodev.Progressor_V001/
│
├── domain/                        → Entidades e regras de negócio
│   ├── person/                    → Person (classe base)
│   ├── student/                   → Entidade Student + exceções
│   ├── trainer/                   → Entidade PersonalTrainer + exceções
│   ├── training/                  → Entidade TrainingPlan + exceções
│   ├── measurement/               → Entidade Measurement + exceções
│   ├── shared/                    → Goal, TrainingLevel, Progressable
│   └── user/                      → User (entidade de auth), Role
│
├── application/                   → Casos de uso, services, DTOs, mappers
│   ├── auth/                      → AuthService, RegisterRequest, LoginRequest, AuthResponse
│   ├── student/                   → StudentService, StudentRequest/Response, StudentMapper
│   ├── trainer/                   → PersonalTrainerService, TrainerRequest/Response, TrainerMapper
│   ├── training/                  → TrainingPlanService, TrainingPlanRequest/Response, TrainingPlanMapper
│   └── measurement/               → MeasurementService, MeasurementRequest/Response, MeasurementMapper
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
    ├── measurement/               → MeasurementController
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

> O backend roda na **porta 8081** (`server.port=8081` no `application.yml`).

---

## 📖 Como Testar a API

### Opção 1 — Swagger UI (browser)

Acesse a documentação interativa:
```
http://localhost:8081/swagger-ui/index.html
```

**Como autenticar no Swagger:**
1. Execute `POST /auth/login` com suas credenciais
2. Copie o valor do campo `token` na resposta
3. Clique no botão **Authorize 🔓** no topo da página
4. Cole: `Bearer <seu_token>`
5. Clique em **Authorize** — todas as requisições seguintes incluirão o token

**Fluxo sugerido de testes no Swagger:**
```
POST /auth/register    → cria conta STUDENT
POST /auth/register    → cria conta TRAINER
POST /auth/login       → login como STUDENT, copia token e autoriza
GET  /students/me      → visualiza perfil do aluno
PUT  /students/me      → atualiza idade, peso, altura, objetivo
POST /auth/login       → login como TRAINER, copia token e autoriza
POST /trainers/me/students/{id}  → vincula aluno ao trainer
POST /training-plans   → cria plano de treino para o aluno
POST /auth/login       → login como STUDENT novamente
GET  /training-plans/me/current  → visualiza plano atual
POST /measurements     → registra uma medição
POST /measurements     → registra uma segunda medição
GET  /measurements/evolution     → visualiza os deltas de evolução
```

---

### Opção 2 — Coleção Postman

Importe o arquivo `docs/Progressor-API.postman_collection.json`.

**Funcionalidades da coleção:**
- URL base configurada como variável `{{baseUrl}}` (`http://localhost:8081`)
- As requisições de login salvam automaticamente `{{studentToken}}` e `{{trainerToken}}` como variáveis de coleção — sem necessidade de copiar manualmente
- Todos os endpoints organizados por módulo: Auth, Students, Trainers, Training Plans, Measurements

**Como começar:**
1. Importe a coleção no Postman: **Import → Upload Files**
2. Execute **Login Student** → token salvo automaticamente
3. Execute **Login Trainer** → token salvo automaticamente
4. Todas as demais requisições usam `{{studentToken}}` ou `{{trainerToken}}` automaticamente

---

## 🌐 Endpoints da API

### Autenticação
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `POST` | `/auth/register` | ❌ | Registrar novo usuário (STUDENT ou TRAINER) |
| `POST` | `/auth/login` | ❌ | Login e recebimento do token JWT |
| `GET` | `/api/users/me` | ✅ | Retornar dados do usuário autenticado |

### Alunos
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `GET` | `/students/me` | ✅ STUDENT | Buscar próprio perfil |
| `PUT` | `/students/me` | ✅ STUDENT | Atualizar próprio perfil |
| `PATCH` | `/students/me/progress` | ✅ STUDENT | Disparar progressão de nível |

### Personal Trainers
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `GET` | `/trainers/me` | ✅ TRAINER | Buscar próprio perfil |
| `POST` | `/trainers/me/students/{id}` | ✅ TRAINER | Vincular aluno |
| `DELETE` | `/trainers/me/students/{id}` | ✅ TRAINER | Desvincular aluno |
| `GET` | `/trainers/me/students` | ✅ TRAINER | Listar alunos supervisionados |

### Planos de Treino
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `POST` | `/training-plans` | ✅ TRAINER | Criar plano para um aluno |
| `POST` | `/training-plans/me` | ✅ STUDENT | Criar próprio plano de treino |
| `GET` | `/training-plans/me/current` | ✅ STUDENT | Buscar plano ativo atual |
| `GET` | `/training-plans/me/history` | ✅ STUDENT | Histórico de planos |
| `GET` | `/training-plans` | ✅ | Listar todos os planos |

### Medidas
| Método | Endpoint | Auth | Descrição |
|---|---|---|---|
| `POST` | `/measurements` | ✅ STUDENT | Registrar nova medição |
| `GET` | `/measurements` | ✅ STUDENT | Listar todas as medições |
| `GET` | `/measurements/evolution` | ✅ STUDENT | Calcular deltas de evolução |
| `GET` | `/measurements/trainer/students/{id}` | ✅ TRAINER | Ver medições de um aluno |

**Campos de medição:**
`weightKg`, `heightCm`, `bodyFatPercent`, `muscleMassPercent`, `waistCm`, `hipCm`, `chestCm`, `rightArmCm`, `leftArmCm`, `rightThighCm`, `leftThighCm`

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
- [x] 🔗 **M11** — Auth: User–Domain Binding (User ↔ Student / Trainer)
- [x] 🎓 **M12** — Student Module (endpoints de auto-serviço)
- [x] 🏋️ **M13** — Personal Trainer Module (perfil + supervisão de alunos)
- [x] 📋 **M14** — Training Plans Module (controle por role + auto-serviço)
- [x] 📏 **M15** — Measurements Module (peso, gordura, braço D/E, coxa D/E...)
- [x] 🎨 **M16** — Frontend (React + Vite + TypeScript + Tailwind)

---

## 🤝 Como Contribuir

### 1. Faça um Fork do repositório
### 2. Clone o seu fork
```bash
git clone https://github.com/SEU_USUARIO/Progressor-V0.0.1.git
```
### 3. Crie uma branch e faça seus commits
```bash
git checkout -b feature/issue-XX-descricao-curta
git commit -m "feat(escopo): descrição curta em inglês"
git push origin feature/issue-XX-descricao-curta
```
Abra um **Pull Request** apontando para a branch `main`.

---

## 📋 Convenção de Commits

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
