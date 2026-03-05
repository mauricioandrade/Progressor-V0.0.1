<div align="center">

<img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/PostgreSQL-Docker-blue?style=for-the-badge&logo=postgresql&logoColor=white"/>
<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>

<br/>
<br/>

# 📈 Progressor

### 🇺🇸 English &nbsp;|&nbsp; 🇧🇷 [Português](#-progressor-1)

</div>

---

## 📌 About the Project

**Progressor** is a gym student management system focused on **evolution**. The platform tracks students, personal trainers, and training plans — automatically evolving each student's level and assigned plan as they progress over time.

> The system grows with the student. From beginner to advanced, every step is tracked.

---

## 🛠️ Technologies

| Technology | Version | Purpose |
|---|---|---|
| Java | 25 | Main language |
| Spring Boot | 4.0.3 | Application framework |
| Spring Web | — | REST API |
| Spring Data JPA | — | Database persistence |
| PostgreSQL | Latest | Relational database |
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

---

## 🗺️ Roadmap

- [x] 🏗️ **M1 — Project Foundation** — Setup, Docker, enums, package structure
- [x] 🧱 **M2 — Core Entities** — Person, Student, PersonalTrainer, TrainingPlan
- [x] ⚙️ **M3 — Business Layer** — Repositories and Services
- [x] 🌐 **M4 — REST API** — Controllers and DTOs
- [x] 📈 **M5 — Progression System** — Progressable interface and evolution logic

---

<br/>
<br/>

---

<div align="center">

# 📈 Progressor

### 🇧🇷 Português &nbsp;|&nbsp; 🇺🇸 [English](#-progressor)

</div>

---

## 📌 Sobre o Projeto

**Progressor** é um sistema de gerenciamento de alunos de academia com foco em **evolução**. A plataforma acompanha alunos, personal trainers e planos de treino — evoluindo automaticamente o nível e o plano de cada aluno conforme ele avança ao longo do tempo.

> O sistema cresce junto com o aluno. Do iniciante ao avançado, cada etapa é registrada.

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 25 | Linguagem principal |
| Spring Boot | 4.0.3 | Framework da aplicação |
| Spring Web | — | API REST |
| Spring Data JPA | — | Persistência de dados |
| PostgreSQL | Latest | Banco de dados relacional |
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

---

## 🗺️ Roadmap

- [x] 🏗️ **M1 — Project Foundation** — Setup, Docker, enums, estrutura de pacotes
- [x] 🧱 **M2 — Core Entities** — Person, Student, PersonalTrainer, TrainingPlan
- [x] ⚙️ **M3 — Business Layer** — Repositórios e Services
- [x] 🌐 **M4 — REST API** — Controllers e DTOs
- [x] 📈 **M5 — Progression System** — Interface Progressable e lógica de evolução

---

<div align="center">
  <sub>Built with 💪 by <a href="https://github.com/mauricioandrade">mauricioandrade</a></sub>
</div>
