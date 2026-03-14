<div align="center">

<img src="https://img.shields.io/badge/status-em%20desenvolvimento-brightgreen?style=for-the-badge" alt="Status"/>
<img src="https://img.shields.io/badge/java-25-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
<img src="https://img.shields.io/badge/spring%20boot-4.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
<img src="https://img.shields.io/badge/react-19-61DAFB?style=for-the-badge&logo=react&logoColor=black" alt="React"/>
<img src="https://img.shields.io/badge/postgresql-18-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL"/>

# 🏋️ Progressor

**EN** · [PT](#português)

> A management platform for personal trainers and nutritionists to track students, prescribe training plans and diets, and monitor physical evolution over time.

**Backend** → `https://github.com/mauricioandrade/Progressor-V0.0.1`
**Frontend** → `https://github.com/mauricioandrade/progressor-frontend`

</div>

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Authentication](#authentication)
- [Roles & Permissions](#roles--permissions)
- [API Reference](#api-reference)
- [Project Structure](#project-structure)
- [Architecture Decisions](#architecture-decisions)
- [Commit Convention](#commit-convention)
- [Português](#português)

---

## Overview

Progressor connects three types of users in a single platform:

| Role | What they do |
|---|---|
| 🎓 **Student** | Tracks own training plan, diet and body measurements |
| 💪 **Trainer** | Links students, creates and assigns training plans |
| 🥗 **Nutritionist** | Links students, creates and assigns diet plans |

A student can have **one trainer** and **one nutritionist** simultaneously. Both professionals can view the student's measurements and current plans to deliver a holistic approach.

---

## Tech Stack

### Backend

| Technology | Version | Purpose |
|---|---|---|
| ☕ Java | 25 | Language |
| 🍃 Spring Boot | 4.0.3 | Framework |
| 🔒 Spring Security | 7.0 | Authentication & authorization |
| 🪙 JWT (jjwt) | 0.12.6 | Stateless tokens |
| 🗄️ Spring Data JPA | 4.0 | Persistence layer |
| 🐘 PostgreSQL | 18 | Database |
| ✅ Bean Validation | 3.1 | Input validation |
| 📖 SpringDoc OpenAPI | 2.8 | Swagger UI |

### Frontend

| Technology | Version | Purpose |
|---|---|---|
| ⚛️ React | 19 | UI framework |
| 📘 TypeScript | 5 | Type safety |
| ⚡ Vite | 6 | Build tool |
| 🛣️ React Router | 7 | Routing |
| 🔗 Axios | — | HTTP client |
| 📊 Recharts | — | Charts |

---

## Getting Started

### Prerequisites

- Java 25+
- Node 20+
- Docker

### 1. Database

```bash
docker run --name progressor-db \
  -e POSTGRES_DB=progressor \
  -e POSTGRES_USER=progressor \
  -e POSTGRES_PASSWORD=progressor \
  -p 5432:5432 \
  -d postgres
```

### 2. Backend

```bash
git clone https://github.com/mauricioandrade/Progressor-V0.0.1
cd Progressor-V0.0.1
./mvnw spring-boot:run
```

> Runs on `http://localhost:8081`
> Swagger UI → `http://localhost:8081/swagger-ui.html`

### 3. Frontend

```bash
git clone https://github.com/mauricioandrade/progressor-frontend
cd progressor-frontend
npm install
npm run dev
```

> Runs on `http://localhost:5173`

### Environment variables (backend)

```yaml
# src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/progressor
    username: progressor
    password: progressor
  jpa:
    hibernate:
      ddl-auto: update

jwt:
  secret: <at-least-32-character-secret-key>
  expiration-ms: 86400000
```

---

## Authentication

All endpoints except `/auth/**`, `/swagger-ui/**` and `/actuator/health` require a JWT:

```
Authorization: Bearer <token>
```

### Register

```http
POST /auth/register
```

```json
{
  "email": "trainer@example.com",
  "password": "Password1",
  "name": "John Smith",
  "phone": "11999999999",
  "cref": "123456-G/SP",
  "role": "TRAINER"
}
```

| Role | Required extra fields |
|---|---|
| `STUDENT` | `birthDate` (yyyy-MM-dd) |
| `TRAINER` | `cref` |
| `NUTRITIONIST` | `crn` |

### Login

```http
POST /auth/login
```

```json
{
  "email": "trainer@example.com",
  "password": "Password1"
}
```

---

## Roles & Permissions

| Role | Description |
|---|---|
| `STUDENT` | Own profile, training plan, diet plan, measurements |
| `TRAINER` | Manage linked students and training plans |
| `NUTRITIONIST` | Manage linked students and diet plans |
| `ADMIN` | Full access |

---

## API Reference

### 🔐 Auth
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/auth/register` | public | Register user |
| POST | `/auth/login` | public | Login, returns JWT |

### 🎓 Students
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/students` | TRAINER, NUTRITIONIST, ADMIN | List all |
| GET | `/students/{id}` | TRAINER, NUTRITIONIST, ADMIN | Find by ID |
| PATCH | `/students/{id}/progress` | TRAINER, ADMIN | Level up student |
| GET | `/students/me` | authenticated | Own profile |
| PUT | `/students/me` | authenticated | Update own profile |
| PATCH | `/students/me/progress` | authenticated | Level up self |
| POST | `/students/me/avatar` | authenticated | Upload avatar |
| GET | `/students/me/avatar` | authenticated | Get avatar |

### 💪 Trainers
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/trainers/me` | TRAINER | Own profile |
| POST | `/trainers/me/avatar` | TRAINER | Upload avatar |
| GET | `/trainers/me/avatar` | TRAINER | Get avatar |
| GET | `/trainers/me/students` | TRAINER | Linked students |
| POST | `/trainers/me/students/{id}` | TRAINER | Link student |
| DELETE | `/trainers/me/students/{id}` | TRAINER | Unlink student |
| GET | `/trainers` | TRAINER, ADMIN | List all |
| GET | `/trainers/{id}` | TRAINER, ADMIN | Find by ID |
| POST | `/trainers` | ADMIN | Register trainer |

### 🥗 Nutritionists
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/nutritionists/me` | NUTRITIONIST | Own profile |
| POST | `/nutritionists/me/avatar` | NUTRITIONIST | Upload avatar |
| GET | `/nutritionists/me/avatar` | NUTRITIONIST | Get avatar |
| GET | `/nutritionists/me/students` | NUTRITIONIST | Linked students |
| POST | `/nutritionists/me/students/{id}` | NUTRITIONIST | Link student |
| DELETE | `/nutritionists/me/students/{id}` | NUTRITIONIST | Unlink student |
| GET | `/nutritionists` | NUTRITIONIST, ADMIN | List all |
| GET | `/nutritionists/{id}` | NUTRITIONIST, ADMIN | Find by ID |

### 🏃 Training Plans
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/training-plans` | TRAINER | Create plan for student |
| PUT | `/training-plans/{id}` | TRAINER | Update plan |
| DELETE | `/training-plans/{id}` | TRAINER | Delete plan |
| POST | `/training-plans/{id}/assign/{studentId}` | TRAINER | Assign to student |
| GET | `/training-plans` | TRAINER, ADMIN | List all |
| GET | `/training-plans/{id}` | TRAINER, ADMIN | Find by ID |
| GET | `/training-plans/me/current` | authenticated | Own current plan |
| GET | `/training-plans/me/history` | authenticated | Own history |
| GET | `/training-plans/student/{id}/current` | TRAINER, NUTRITIONIST, ADMIN | Student's current plan |

### 🥦 Diet Plans
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/diet-plans` | NUTRITIONIST | Create plan for student |
| PUT | `/diet-plans/{id}` | NUTRITIONIST | Update plan |
| DELETE | `/diet-plans/{id}` | NUTRITIONIST | Delete plan |
| POST | `/diet-plans/{id}/assign/{studentId}` | NUTRITIONIST | Assign to student |
| GET | `/diet-plans` | NUTRITIONIST, ADMIN | List all |
| GET | `/diet-plans/{id}` | NUTRITIONIST, ADMIN | Find by ID |
| GET | `/diet-plans/me/current` | authenticated | Own current diet |
| GET | `/diet-plans/student/{id}/current` | TRAINER, NUTRITIONIST, ADMIN | Student's current diet |

### 📏 Measurements
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/measurements` | authenticated | Record measurement |
| GET | `/measurements` | authenticated | Own measurements |
| GET | `/measurements/evolution` | authenticated | Own evolution |
| GET | `/measurements/students/{id}` | TRAINER, NUTRITIONIST, ADMIN | Student's measurements |
| GET | `/measurements/students/{id}/evolution` | TRAINER, NUTRITIONIST, ADMIN | Student's evolution |

---

## Project Structure

```
src/main/java/dev/mauriciodev/progressor/
│
├── 📂 application/               # Use cases — orchestration only, no business rules
│   ├── auth/                     # AuthService, RegisterRequest, LoginRequest, AuthResponse
│   ├── measurement/              # MeasurementService, Mapper, Request, Response, EvolutionResponse
│   ├── nutrition/                # DietPlanService, Mapper, Request, UpdateRequest, Response
│   ├── nutritionist/             # NutritionistService, Mapper, Response
│   ├── student/                  # StudentService, Mapper, UpdateRequest, Response
│   ├── trainer/                  # PersonalTrainerService, Mapper, Request, Response
│   └── training/                 # TrainingPlanService, Mapper, Request, UpdateRequest, Response
│
├── 📂 domain/                    # Business rules — rich entities, value objects, exceptions
│   ├── measurement/              # Measurement, MeasurementDelta
│   ├── nutrition/                # DietPlan, Meal
│   ├── nutritionist/             # Nutritionist
│   ├── person/                   # Person (abstract base)
│   ├── shared/                   # Goal, TrainingLevel, DietFocus, Progressable
│   ├── student/                  # Student
│   ├── trainer/                  # PersonalTrainer
│   ├── training/                 # TrainingPlan, Exercise
│   └── user/                     # User, Role
│
├── 📂 infrastructure/            # Adapters — DB, security, external services
│   ├── persistence/              # JPA repositories
│   └── security/                 # JwtService, JwtAuthFilter, SecurityConfig
│
└── 📂 presentation/              # Controllers, exception handlers
    ├── auth/
    ├── exception/                # GlobalExceptionHandler, ErrorResponse
    ├── measurement/
    ├── nutrition/
    ├── nutritionist/
    ├── student/
    ├── trainer/
    ├── training/
    └── UserController.java
```

---

## Architecture Decisions

### No Lombok
Lombok generates invisible code that hides what the compiler actually sees. Every constructor, getter and method is written explicitly to keep the codebase readable, debuggable and easy to learn from.

### Constructor injection only — no `@Autowired`
Constructor injection makes dependencies explicit in the class signature, enables immutable fields (`final`), makes unit testing straightforward without a Spring context, and is the approach recommended by Spring itself since version 4. Field injection with `@Autowired` hides dependencies and makes cycles harder to detect.

### Rich domain entities
Entities are not plain data bags. Business rules live in the domain:
- `Student.evolve()` — encapsulates level progression logic
- `Student.assignTrainingPlan()` — maintains training history
- `Student.assignDietPlan()` — links diet to student
- `Measurement.calculateEvolutionFrom(baseline)` — computes body evolution deltas
- `TrainingPlan.update()` / `DietPlan.update()` — controlled mutation via method, not public setters
- `Person.setName()` / `Person.setPhone()` — validation at the base class

### Protected no-arg constructor without validation
JPA requires a no-argument constructor to instantiate entities during bootstrap (unsaved-value inference). This constructor is `protected` and contains no validation — rules live only in the public constructor that takes arguments.

### Immutable collection views
`TrainingPlan.getExercises()` and `DietPlan.getMeals()` return `Collections.unmodifiableList()`. External code cannot mutate internal state directly. Mutation only occurs through `update()`, which replaces the entire list after validation.

### `MeasurementDelta` as a domain Value Object
Computing the evolution between two measurements is domain knowledge, not service knowledge. `Measurement.calculateEvolutionFrom(baseline)` returns a `MeasurementDelta` that the service simply unpacks to build the response, keeping the logic testable inside the entity.

### `@PreAuthorize` on controllers
Role checks moved from method bodies (fragile `if` statements easy to forget) to declarative annotations. Spring enforces them before the method is invoked. `@EnableMethodSecurity` on `SecurityConfig` activates this — without it annotations are silently ignored.

### Separate `TrainingPlanUpdateRequest` and `DietPlanUpdateRequest`
Create requests include `studentId` to link the plan on creation. Update requests do not — the plan ID comes from the URL. Reusing the same record would silently ignore `studentId` on updates, which is misleading.

### `POST /students` removed
The direct student registration endpoint created a `Student` without an associated `User`, with no authentication. The correct flow is `POST /auth/register` with `role: STUDENT`, which creates both `User` and `Student` in a single transaction.

### Nutritionist module mirrors the trainer module
`Nutritionist` / `DietPlan` / `Meal` follow the same patterns as `PersonalTrainer` / `TrainingPlan` / `Exercise`. Consistency lowers cognitive load — understanding one module means understanding the other.

### Nutritionist gets read access to training and measurement endpoints
A nutritionist needs full student context to prescribe a diet: training level, body measurements, current training plan. Therefore `GET /students/{id}`, `GET /measurements/students/{id}` and `GET /training-plans/student/{id}/current` accept the `NUTRITIONIST` role.

---

## Commit Convention

Following [Conventional Commits](https://www.conventionalcommits.org/):

```
type(scope): message

types:  feat | fix | refactor | docs | test | chore
scopes: domain | auth | student | trainer | nutritionist | training | nutrition | security | frontend
```

Examples:
```
feat(nutrition): add nutritionist module with diet plan management
refactor(domain): add validation to Person base setters and constructor
fix(domain): use no-arg super() in JPA constructors to avoid validation on bootstrap
feat(security): replace manual role checks with @PreAuthorize
docs: add project README with architecture decisions and endpoint reference
```

---

---

<div id="português">

# 🏋️ Progressor — Documentação em Português

> Plataforma de gestão de alunos para personal trainers e nutricionistas. Permite vincular alunos a profissionais, gerenciar planos de treino, planos de dieta e acompanhar a evolução física ao longo do tempo.

**Backend** → `https://github.com/mauricioandrade/Progressor-V0.0.1`
**Frontend** → `https://github.com/mauricioandrade/progressor-frontend`

</div>

---

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Stack](#stack)
- [Como Rodar](#como-rodar)
- [Autenticação](#autenticação)
- [Roles e Permissões](#roles-e-permissões)
- [Referência da API](#referência-da-api)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Decisões de Arquitetura](#decisões-de-arquitetura)
- [Convenção de Commits](#convenção-de-commits)

---

## Visão Geral

O Progressor conecta três tipos de usuários em uma única plataforma:

| Role | O que faz |
|---|---|
| 🎓 **Aluno** | Acompanha seu plano de treino, dieta e medições corporais |
| 💪 **Personal Trainer** | Vincula alunos, cria e atribui planos de treino |
| 🥗 **Nutricionista** | Vincula alunos, cria e atribui planos de dieta |

Um aluno pode ter **um trainer** e **um nutricionista** simultaneamente. Ambos os profissionais podem visualizar as medições e planos atuais do aluno para uma abordagem integrada.

---

## Stack

### Backend

| Tecnologia | Versão | Finalidade |
|---|---|---|
| ☕ Java | 25 | Linguagem |
| 🍃 Spring Boot | 4.0.3 | Framework |
| 🔒 Spring Security | 7.0 | Autenticação e autorização |
| 🪙 JWT (jjwt) | 0.12.6 | Tokens stateless |
| 🗄️ Spring Data JPA | 4.0 | Camada de persistência |
| 🐘 PostgreSQL | 18 | Banco de dados |
| ✅ Bean Validation | 3.1 | Validação de entrada |
| 📖 SpringDoc OpenAPI | 2.8 | Swagger UI |

### Frontend

| Tecnologia | Versão | Finalidade |
|---|---|---|
| ⚛️ React | 19 | Framework UI |
| 📘 TypeScript | 5 | Tipagem estática |
| ⚡ Vite | 6 | Build tool |
| 🛣️ React Router | 7 | Roteamento |
| 🔗 Axios | — | Cliente HTTP |
| 📊 Recharts | — | Gráficos |

---

## Como Rodar

### Pré-requisitos

- Java 25+
- Node 20+
- Docker

### 1. Banco de dados

```bash
docker run --name progressor-db \
  -e POSTGRES_DB=progressor \
  -e POSTGRES_USER=progressor \
  -e POSTGRES_PASSWORD=progressor \
  -p 5432:5432 \
  -d postgres
```

### 2. Backend

```bash
git clone https://github.com/mauricioandrade/Progressor-V0.0.1
cd Progressor-V0.0.1
./mvnw spring-boot:run
```

> Sobe em `http://localhost:8081`
> Swagger UI → `http://localhost:8081/swagger-ui.html`

### 3. Frontend

```bash
git clone https://github.com/mauricioandrade/progressor-frontend
cd progressor-frontend
npm install
npm run dev
```

> Sobe em `http://localhost:5173`

### Variáveis de ambiente (backend)

```yaml
# src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/progressor
    username: progressor
    password: progressor
  jpa:
    hibernate:
      ddl-auto: update

jwt:
  secret: <chave-com-minimo-32-caracteres>
  expiration-ms: 86400000
```

---

## Autenticação

Todos os endpoints exceto `/auth/**`, `/swagger-ui/**` e `/actuator/health` exigem JWT:

```
Authorization: Bearer <token>
```

### Registro

```http
POST /auth/register
```

```json
{
  "email": "trainer@example.com",
  "password": "Senha123",
  "name": "João Silva",
  "phone": "11999999999",
  "cref": "123456-G/SP",
  "role": "TRAINER"
}
```

| Role | Campos extras obrigatórios |
|---|---|
| `STUDENT` | `birthDate` (yyyy-MM-dd) |
| `TRAINER` | `cref` |
| `NUTRITIONIST` | `crn` |

### Login

```http
POST /auth/login
```

```json
{
  "email": "trainer@example.com",
  "password": "Senha123"
}
```

---

## Roles e Permissões

| Role | Descrição |
|---|---|
| `STUDENT` | Perfil próprio, plano de treino, dieta e medições |
| `TRAINER` | Gerencia alunos vinculados e planos de treino |
| `NUTRITIONIST` | Gerencia alunos vinculados e planos de dieta |
| `ADMIN` | Acesso total |

---

## Referência da API

### 🔐 Auth
| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| POST | `/auth/register` | público | Cadastro de usuário |
| POST | `/auth/login` | público | Login, retorna JWT |

### 🎓 Alunos
| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| GET | `/students` | TRAINER, NUTRITIONIST, ADMIN | Listar todos |
| GET | `/students/{id}` | TRAINER, NUTRITIONIST, ADMIN | Buscar por ID |
| PATCH | `/students/{id}/progress` | TRAINER, ADMIN | Evoluir nível |
| GET | `/students/me` | autenticado | Perfil próprio |
| PUT | `/students/me` | autenticado | Atualizar perfil |
| PATCH | `/students/me/progress` | autenticado | Evoluir próprio nível |
| POST | `/students/me/avatar` | autenticado | Upload de avatar |
| GET | `/students/me/avatar` | autenticado | Buscar avatar |

### 💪 Trainers
| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| GET | `/trainers/me` | TRAINER | Perfil próprio |
| POST | `/trainers/me/avatar` | TRAINER | Upload de avatar |
| GET | `/trainers/me/avatar` | TRAINER | Buscar avatar |
| GET | `/trainers/me/students` | TRAINER | Alunos vinculados |
| POST | `/trainers/me/students/{id}` | TRAINER | Vincular aluno |
| DELETE | `/trainers/me/students/{id}` | TRAINER | Desvincular aluno |
| GET | `/trainers` | TRAINER, ADMIN | Listar todos |
| GET | `/trainers/{id}` | TRAINER, ADMIN | Buscar por ID |
| POST | `/trainers` | ADMIN | Cadastrar trainer |

### 🥗 Nutricionistas
| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| GET | `/nutritionists/me` | NUTRITIONIST | Perfil próprio |
| POST | `/nutritionists/me/avatar` | NUTRITIONIST | Upload de avatar |
| GET | `/nutritionists/me/avatar` | NUTRITIONIST | Buscar avatar |
| GET | `/nutritionists/me/students` | NUTRITIONIST | Alunos vinculados |
| POST | `/nutritionists/me/students/{id}` | NUTRITIONIST | Vincular aluno |
| DELETE | `/nutritionists/me/students/{id}` | NUTRITIONIST | Desvincular aluno |
| GET | `/nutritionists` | NUTRITIONIST, ADMIN | Listar todos |
| GET | `/nutritionists/{id}` | NUTRITIONIST, ADMIN | Buscar por ID |

### 🏃 Planos de Treino
| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| POST | `/training-plans` | TRAINER | Criar plano para aluno |
| PUT | `/training-plans/{id}` | TRAINER | Atualizar plano |
| DELETE | `/training-plans/{id}` | TRAINER | Remover plano |
| POST | `/training-plans/{id}/assign/{studentId}` | TRAINER | Alocar a aluno |
| GET | `/training-plans` | TRAINER, ADMIN | Listar todos |
| GET | `/training-plans/{id}` | TRAINER, ADMIN | Buscar por ID |
| GET | `/training-plans/me/current` | autenticado | Plano atual do aluno logado |
| GET | `/training-plans/me/history` | autenticado | Histórico do aluno logado |
| GET | `/training-plans/student/{id}/current` | TRAINER, NUTRITIONIST, ADMIN | Plano atual de um aluno |

### 🥦 Planos de Dieta
| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| POST | `/diet-plans` | NUTRITIONIST | Criar plano para aluno |
| PUT | `/diet-plans/{id}` | NUTRITIONIST | Atualizar plano |
| DELETE | `/diet-plans/{id}` | NUTRITIONIST | Remover plano |
| POST | `/diet-plans/{id}/assign/{studentId}` | NUTRITIONIST | Alocar a aluno |
| GET | `/diet-plans` | NUTRITIONIST, ADMIN | Listar todos |
| GET | `/diet-plans/{id}` | NUTRITIONIST, ADMIN | Buscar por ID |
| GET | `/diet-plans/me/current` | autenticado | Dieta atual do aluno logado |
| GET | `/diet-plans/student/{id}/current` | TRAINER, NUTRITIONIST, ADMIN | Dieta atual de um aluno |

### 📏 Medições
| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| POST | `/measurements` | autenticado | Registrar medição |
| GET | `/measurements` | autenticado | Próprias medições |
| GET | `/measurements/evolution` | autenticado | Própria evolução |
| GET | `/measurements/students/{id}` | TRAINER, NUTRITIONIST, ADMIN | Medições de um aluno |
| GET | `/measurements/students/{id}/evolution` | TRAINER, NUTRITIONIST, ADMIN | Evolução de um aluno |

---

## Estrutura do Projeto

```
src/main/java/dev/mauriciodev/progressor/
│
├── 📂 application/               # Casos de uso — orquestração, sem regras de negócio
│   ├── auth/
│   ├── measurement/
│   ├── nutrition/
│   ├── nutritionist/
│   ├── student/
│   ├── trainer/
│   └── training/
│
├── 📂 domain/                    # Regras de negócio — entidades ricas, value objects, exceções
│   ├── measurement/              # Measurement, MeasurementDelta
│   ├── nutrition/                # DietPlan, Meal
│   ├── nutritionist/             # Nutritionist
│   ├── person/                   # Person (base abstrata)
│   ├── shared/                   # Goal, TrainingLevel, DietFocus, Progressable
│   ├── student/                  # Student
│   ├── trainer/                  # PersonalTrainer
│   ├── training/                 # TrainingPlan, Exercise
│   └── user/                     # User, Role
│
├── 📂 infrastructure/            # Adaptadores — banco, segurança, serviços externos
│   ├── persistence/              # Repositórios JPA
│   └── security/                 # JwtService, JwtAuthFilter, SecurityConfig
│
└── 📂 presentation/              # Controllers e tratamento de exceções
    ├── auth/
    ├── exception/
    ├── measurement/
    ├── nutrition/
    ├── nutritionist/
    ├── student/
    ├── trainer/
    ├── training/
    └── UserController.java
```

---

## Decisões de Arquitetura

### Sem Lombok
Lombok gera código invisível que dificulta o aprendizado e o debug. Todo boilerplate é escrito explicitamente para manter o código legível e rastreável.

### Sem `@Autowired` — constructor injection
Injeção por construtor torna as dependências explícitas, permite campos `final` imutáveis, facilita testes unitários sem contexto Spring e é a abordagem recomendada pelo próprio Spring desde a versão 4. `@Autowired` em campo oculta dependências e dificulta a detecção de ciclos.

### Entidades com comportamento rico
Entidades não são simples bags de dados. Regras de negócio vivem no domínio:
- `Student.evolve()` — encapsula a lógica de progressão de nível
- `Student.assignTrainingPlan()` — mantém o histórico de treinos
- `Student.assignDietPlan()` — vincula dieta ao aluno
- `Measurement.calculateEvolutionFrom(baseline)` — calcula deltas de evolução física
- `TrainingPlan.update()` / `DietPlan.update()` — mutação controlada via método, não setters
- `Person.setName()` / `Person.setPhone()` — validação na própria base

### Construtor no-arg protegido sem validação
O JPA exige um construtor sem argumentos para instanciar entidades durante o bootstrap. Esse construtor é `protected` e não contém validação — as regras ficam apenas no construtor público com argumentos.

### Visões imutáveis das coleções
`TrainingPlan.getExercises()` e `DietPlan.getMeals()` retornam `Collections.unmodifiableList()`. Código externo não consegue modificar o estado interno diretamente. A mutação só ocorre via `update()`, que substitui a lista inteira após validação.

### `MeasurementDelta` como Value Object de domínio
O cálculo de evolução entre duas medições é conhecimento do domínio, não do serviço. `Measurement.calculateEvolutionFrom(baseline)` retorna um `MeasurementDelta` que o serviço apenas desempacota para montar o response, mantendo a lógica testável na entidade.

### `@PreAuthorize` nos controllers
As verificações de role saíram dos métodos (ifs frágeis fáceis de esquecer) para anotações declarativas. O Spring garante a verificação antes de o método ser invocado. `@EnableMethodSecurity` no `SecurityConfig` ativa esse mecanismo — sem ele as anotações são ignoradas silenciosamente.

### `TrainingPlanUpdateRequest` e `DietPlanUpdateRequest` separados
Requests de criação incluem `studentId` para vincular o plano ao aluno. Requests de atualização não precisam — o ID vem pela URL. Reutilizar o mesmo record forçaria `studentId` a ser ignorado silenciosamente no update.

### `POST /students` removido
O endpoint criava um `Student` sem `User` associado, sem autenticação. O fluxo correto é `POST /auth/register` com `role: STUDENT`, que cria `User` e `Student` em uma única transação atômica.

### Módulo de nutrição espelha o de treino
`Nutritionist` / `DietPlan` / `Meal` seguem exatamente os mesmos padrões de `PersonalTrainer` / `TrainingPlan` / `Exercise`. A consistência reduz a carga cognitiva — entender um módulo significa entender o outro.

### Nutricionista com acesso de leitura aos endpoints de treino e medição
O nutricionista precisa de contexto completo do aluno para prescrever uma dieta: nível de treino, medições corporais, plano de treino atual. Por isso `GET /students/{id}`, `GET /measurements/students/{id}` e `GET /training-plans/student/{id}/current` aceitam a role `NUTRITIONIST`.

---

## Convenção de Commits

Seguimos [Conventional Commits](https://www.conventionalcommits.org/):

```
tipo(escopo): mensagem

tipos:   feat | fix | refactor | docs | test | chore
escopos: domain | auth | student | trainer | nutritionist | training | nutrition | security | frontend
```

Exemplos:
```
feat(nutrition): add nutritionist module with diet plan management
refactor(domain): add validation to Person base setters and constructor
fix(domain): use no-arg super() in JPA constructors to avoid validation on bootstrap
feat(security): replace manual role checks with @PreAuthorize
docs: add project README with architecture decisions and endpoint reference
```

---

<div align="center">

Made with ☕ and 💪

</div>
