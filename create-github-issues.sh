#!/bin/bash

OWNER="mauricioandrade"
REPO="Progressor-V0.0.1"

echo "🚀 Creating milestones and issues for $OWNER/$REPO..."
echo ""

# ─────────────────────────────────────────
# MILESTONES
# ─────────────────────────────────────────

echo "📌 Creating milestones..."

M1=$(gh api repos/$OWNER/$REPO/milestones \
  --method POST \
  --field title="🏗️ M1 — Project Foundation" \
  --field description="Estrutura base do projeto, configurações, Docker e domínio central" \
  --jq '.number')
echo "✅ M1 created (#$M1)"

M2=$(gh api repos/$OWNER/$REPO/milestones \
  --method POST \
  --field title="🧱 M2 — Core Entities" \
  --field description="Implementação das entidades principais com JPA" \
  --jq '.number')
echo "✅ M2 created (#$M2)"

M3=$(gh api repos/$OWNER/$REPO/milestones \
  --method POST \
  --field title="⚙️ M3 — Business Layer" \
  --field description="Services, regras de negócio e lógica de progressão" \
  --jq '.number')
echo "✅ M3 created (#$M3)"

M4=$(gh api repos/$OWNER/$REPO/milestones \
  --method POST \
  --field title="🌐 M4 — REST API" \
  --field description="Controllers e endpoints expostos" \
  --jq '.number')
echo "✅ M4 created (#$M4)"

M5=$(gh api repos/$OWNER/$REPO/milestones \
  --method POST \
  --field title="📈 M5 — Progression System" \
  --field description="O coração do Progressor — evolução do aluno ao longo do tempo" \
  --jq '.number')
echo "✅ M5 created (#$M5)"

echo ""
echo "📋 Creating issues..."
echo ""

# ─────────────────────────────────────────
# M1 — PROJECT FOUNDATION
# ─────────────────────────────────────────

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M1 \
  --field title="📦 #01 — Set up Spring Boot project with Maven, Web, JPA and PostgreSQL" \
  --field body="## 📌 Description
Initialize the Progressor project using Spring Initializr with Maven,
adding dependencies for REST API and PostgreSQL persistence.

## ✅ Acceptance Criteria
- [ ] Project created with Spring Web, Spring Data JPA and PostgreSQL Driver
- [ ] Maven \`pom.xml\` configured with \`spring-boot-starter-parent\`
- [ ] Application compiles without errors

## 🏷️ Labels
\`setup\` \`backend\`" > /dev/null
echo "✅ Issue #01 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M1 \
  --field title="🐳 #02 — Set up PostgreSQL with Docker" \
  --field body="## 📌 Description
Create a docker-compose.yml to spin up a PostgreSQL instance locally,
avoiding the need for a manual database installation.

## ✅ Acceptance Criteria
- [ ] \`docker-compose.yml\` created with PostgreSQL service
- [ ] Database named \`progressor\`
- [ ] Container runs with \`docker compose up -d\`
- [ ] Port 5432 exposed to localhost

## 🔗 Related Issues
- Depends on #01

## 🏷️ Labels
\`setup\` \`docker\` \`infra\`" > /dev/null
echo "✅ Issue #02 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M1 \
  --field title="⚙️ #03 — Configure application.properties for PostgreSQL and JPA" \
  --field body="## 📌 Description
Set up application.properties to connect the Spring Boot application
to the PostgreSQL container and configure JPA behavior.

## ✅ Acceptance Criteria
- [ ] Datasource pointing to localhost:5432/progressor
- [ ] JPA set to \`update\` strategy
- [ ] \`spring.jpa.show-sql=true\` enabled for development
- [ ] Application starts and connects to the database without errors

## 🔗 Related Issues
- Depends on #02

## 🏷️ Labels
\`setup\` \`config\`" > /dev/null
echo "✅ Issue #03 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M1 \
  --field title="🗂️ #04 — Define package structure" \
  --field body="## 📌 Description
Create the base package structure that will organize the project layers
clearly and consistently throughout development.

## ✅ Acceptance Criteria
- [ ] Packages created: \`controller\`, \`service\`, \`repository\`, \`domain/entity\`, \`domain/enums\`, \`domain/interfaces\`
- [ ] Structure follows single responsibility per layer

## 🔗 Related Issues
- Depends on #01

## 🏷️ Labels
\`setup\` \`architecture\`" > /dev/null
echo "✅ Issue #04 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M1 \
  --field title="🎚️ #05 — Create TrainingLevel enum" \
  --field body="## 📌 Description
Define the TrainingLevel enum to represent the possible levels a student
or training plan can have within the progression system.

## ✅ Acceptance Criteria
- [ ] Enum created with values: BEGINNER, INTERMEDIATE, ADVANCED
- [ ] Placed under \`domain/enums\`

## 🔗 Related Issues
- Depends on #04

## 🏷️ Labels
\`domain\` \`enum\`" > /dev/null
echo "✅ Issue #05 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M1 \
  --field title="🎯 #06 — Create Goal enum" \
  --field body="## 📌 Description
Define the Goal enum to represent the student's fitness objective,
which will influence training plan assignment and progression logic.

## ✅ Acceptance Criteria
- [ ] Enum created with values: WEIGHT_LOSS, HYPERTROPHY, CONDITIONING
- [ ] Placed under \`domain/enums\`

## 🔗 Related Issues
- Depends on #04

## 🏷️ Labels
\`domain\` \`enum\`" > /dev/null
echo "✅ Issue #06 created"

echo ""

# ─────────────────────────────────────────
# M2 — CORE ENTITIES
# ─────────────────────────────────────────

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M2 \
  --field title="👤 #07 — Create base class Person" \
  --field body="## 📌 Description
Create the Person abstract class annotated with @MappedSuperclass to serve
as the base for Student and PersonalTrainer, holding shared attributes.

## ✅ Acceptance Criteria
- [ ] Class annotated with \`@MappedSuperclass\`
- [ ] Fields: \`id\`, \`name\`, \`email\`, \`phone\`
- [ ] All fields encapsulated with constructor injection
- [ ] No Lombok — getters written manually

## 🔗 Related Issues
- Depends on #04

## 🏷️ Labels
\`domain\` \`entity\`" > /dev/null
echo "✅ Issue #07 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M2 \
  --field title="🎓 #08 — Create entity Student" \
  --field body="## 📌 Description
Create the Student entity extending Person, with fitness-specific
attributes and the current training plan reference.

## ✅ Acceptance Criteria
- [ ] Extends \`Person\`
- [ ] Fields: \`age\`, \`weight\`, \`height\`, \`goal\` (enum), \`trainingLevel\` (enum)
- [ ] Annotated with \`@Entity\`
- [ ] Current training plan mapped (nullable at creation)

## 🔗 Related Issues
- Depends on #07, #05, #06

## 🏷️ Labels
\`domain\` \`entity\`" > /dev/null
echo "✅ Issue #08 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M2 \
  --field title="💪 #09 — Create entity PersonalTrainer" \
  --field body="## 📌 Description
Create the PersonalTrainer entity extending Person, with professional
attributes specific to a gym trainer.

## ✅ Acceptance Criteria
- [ ] Extends \`Person\`
- [ ] Fields: \`cref\`, \`specialty\`
- [ ] Annotated with \`@Entity\`

## 🔗 Related Issues
- Depends on #07

## 🏷️ Labels
\`domain\` \`entity\`" > /dev/null
echo "✅ Issue #09 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M2 \
  --field title="📋 #10 — Create entity TrainingPlan" \
  --field body="## 📌 Description
Create the TrainingPlan entity representing a structured workout plan
that can be assigned to a student.

## ✅ Acceptance Criteria
- [ ] Fields: \`id\`, \`name\`, \`durationWeeks\`, \`level\` (enum), \`exercises\`
- [ ] Annotated with \`@Entity\`
- [ ] Placed under \`domain/entity\`

## 🔗 Related Issues
- Depends on #05

## 🏷️ Labels
\`domain\` \`entity\`" > /dev/null
echo "✅ Issue #10 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M2 \
  --field title="🔗 #11 — Map relationship Student → TrainingPlan" \
  --field body="## 📌 Description
Map the active training plan relationship in the Student entity
using JPA annotations.

## ✅ Acceptance Criteria
- [ ] \`@ManyToOne\` mapping from Student to TrainingPlan
- [ ] Relationship is optional at student creation
- [ ] Foreign key visible in database

## 🔗 Related Issues
- Depends on #08, #10

## 🏷️ Labels
\`domain\` \`jpa\`" > /dev/null
echo "✅ Issue #11 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M2 \
  --field title="📜 #12 — Map training plan history in Student" \
  --field body="## 📌 Description
Add a history list of training plans to the Student entity so the system
can track how the student has progressed over time.

## ✅ Acceptance Criteria
- [ ] \`@OneToMany\` list of TrainingPlan in Student
- [ ] History is persisted separately from current plan
- [ ] Past plans are not deleted when a new one is assigned

## 🔗 Related Issues
- Depends on #11

## 🏷️ Labels
\`domain\` \`jpa\` \`progression\`" > /dev/null
echo "✅ Issue #12 created"

echo ""

# ─────────────────────────────────────────
# M3 — BUSINESS LAYER
# ─────────────────────────────────────────

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M3 \
  --field title="🗄️ #13 — Create StudentRepository and PersonalTrainerRepository" \
  --field body="## 📌 Description
Create JPA repositories for Student and PersonalTrainer to handle
database operations.

## ✅ Acceptance Criteria
- [ ] Both extend \`JpaRepository\`
- [ ] Placed under \`repository\` package

## 🔗 Related Issues
- Depends on #08, #09

## 🏷️ Labels
\`repository\` \`backend\`" > /dev/null
echo "✅ Issue #13 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M3 \
  --field title="🗄️ #14 — Create TrainingPlanRepository" \
  --field body="## 📌 Description
Create the JPA repository for TrainingPlan with a custom query
to find plans by level.

## ✅ Acceptance Criteria
- [ ] Extends \`JpaRepository\`
- [ ] Custom method: \`findByLevel(TrainingLevel level)\`

## 🔗 Related Issues
- Depends on #10

## 🏷️ Labels
\`repository\` \`backend\`" > /dev/null
echo "✅ Issue #14 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M3 \
  --field title="🧠 #15 — Create StudentService" \
  --field body="## 📌 Description
Implement the service layer for Student with registration
and lookup logic.

## ✅ Acceptance Criteria
- [ ] Methods: \`register()\`, \`findById()\`, \`findAll()\`
- [ ] Uses constructor injection (no \`@Autowired\`)
- [ ] Throws meaningful exceptions when student is not found

## 🔗 Related Issues
- Depends on #13

## 🏷️ Labels
\`service\` \`backend\`" > /dev/null
echo "✅ Issue #15 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M3 \
  --field title="🧠 #16 — Create PersonalTrainerService" \
  --field body="## 📌 Description
Implement the service layer for PersonalTrainer with registration
and lookup logic.

## ✅ Acceptance Criteria
- [ ] Methods: \`register()\`, \`findById()\`, \`findAll()\`
- [ ] Uses constructor injection

## 🔗 Related Issues
- Depends on #13

## 🏷️ Labels
\`service\` \`backend\`" > /dev/null
echo "✅ Issue #16 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M3 \
  --field title="🧠 #17 — Create TrainingPlanService" \
  --field body="## 📌 Description
Implement the service layer for TrainingPlan including assignment
to a student.

## ✅ Acceptance Criteria
- [ ] Methods: \`create()\`, \`findById()\`, \`findAll()\`, \`assignToStudent()\`
- [ ] \`assignToStudent()\` updates current plan and appends to history

## 🔗 Related Issues
- Depends on #14, #15

## 🏷️ Labels
\`service\` \`backend\`" > /dev/null
echo "✅ Issue #17 created"

echo ""

# ─────────────────────────────────────────
# M4 — REST API
# ─────────────────────────────────────────

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M4 \
  --field title="📨 #18 — Create DTOs for request and response" \
  --field body="## 📌 Description
Create Data Transfer Objects to decouple the API layer from the domain
entities, ensuring clean request and response contracts.

## ✅ Acceptance Criteria
- [ ] \`StudentRequest\` and \`StudentResponse\`
- [ ] \`TrainerRequest\` and \`TrainerResponse\`
- [ ] \`TrainingPlanRequest\` and \`TrainingPlanResponse\`
- [ ] No entity exposed directly in controllers

## 🔗 Related Issues
- Depends on #08, #09, #10

## 🏷️ Labels
\`dto\` \`api\`" > /dev/null
echo "✅ Issue #18 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M4 \
  --field title="🎮 #19 — Create StudentController" \
  --field body="## 📌 Description
Expose REST endpoints for student registration and retrieval.

## ✅ Acceptance Criteria
- [ ] \`POST /students\` — register a new student
- [ ] \`GET /students\` — list all students
- [ ] \`GET /students/{id}\` — find by id
- [ ] Returns appropriate HTTP status codes

## 🔗 Related Issues
- Depends on #15, #18

## 🏷️ Labels
\`controller\` \`api\`" > /dev/null
echo "✅ Issue #19 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M4 \
  --field title="🎮 #20 — Create PersonalTrainerController" \
  --field body="## 📌 Description
Expose REST endpoints for personal trainer registration and retrieval.

## ✅ Acceptance Criteria
- [ ] \`POST /trainers\` — register a new trainer
- [ ] \`GET /trainers\` — list all trainers
- [ ] \`GET /trainers/{id}\` — find by id

## 🔗 Related Issues
- Depends on #16, #18

## 🏷️ Labels
\`controller\` \`api\`" > /dev/null
echo "✅ Issue #20 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M4 \
  --field title="🎮 #21 — Create TrainingPlanController" \
  --field body="## 📌 Description
Expose REST endpoints for training plan creation and assignment.

## ✅ Acceptance Criteria
- [ ] \`POST /training-plans\` — create a new plan
- [ ] \`GET /training-plans\` — list all plans
- [ ] \`POST /training-plans/{id}/assign/{studentId}\` — assign to student

## 🔗 Related Issues
- Depends on #17, #18

## 🏷️ Labels
\`controller\` \`api\`" > /dev/null
echo "✅ Issue #21 created"

echo ""

# ─────────────────────────────────────────
# M5 — PROGRESSION SYSTEM
# ─────────────────────────────────────────

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M5 \
  --field title="🔌 #22 — Implement interface Progressable" \
  --field body="## 📌 Description
Create the Progressable interface that defines the contract for any
entity that can evolve within the Progressor system.

## ✅ Acceptance Criteria
- [ ] Interface with methods: \`evolve()\` and \`evaluateProgress()\`
- [ ] Placed under \`domain/interfaces\`
- [ ] \`Student\` implements \`Progressable\`

## 🔗 Related Issues
- Depends on #08

## 🏷️ Labels
\`domain\` \`interface\` \`progression\`" > /dev/null
echo "✅ Issue #22 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M5 \
  --field title="🧬 #23 — Implement progression logic in StudentService" \
  --field body="## 📌 Description
Implement the logic that evaluates a student's progress and automatically
evolves their training level and assigned plan when criteria are met.

## ✅ Acceptance Criteria
- [ ] Level advances after a configurable number of weeks
- [ ] New plan assigned based on new level and goal
- [ ] Previous plan moved to history
- [ ] No level above ADVANCED

## 🔗 Related Issues
- Depends on #22, #17

## 🏷️ Labels
\`service\` \`progression\`" > /dev/null
echo "✅ Issue #23 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M5 \
  --field title="🚀 #24 — Create endpoint PATCH /students/{id}/progress" \
  --field body="## 📌 Description
Expose an endpoint to manually trigger or evaluate the progression
of a specific student.

## ✅ Acceptance Criteria
- [ ] \`PATCH /students/{id}/progress\`
- [ ] Returns updated student with new level and plan
- [ ] Returns 400 if student is already at ADVANCED level

## 🔗 Related Issues
- Depends on #23, #19

## 🏷️ Labels
\`controller\` \`api\` \`progression\`" > /dev/null
echo "✅ Issue #24 created"

gh api repos/$OWNER/$REPO/issues \
  --method POST \
  --field milestone=$M5 \
  --field title="📜 #25 — Persist and expose training plan history per student" \
  --field body="## 📌 Description
Ensure the student's training plan history is persisted correctly and
exposed via the API so progress can be tracked over time.

## ✅ Acceptance Criteria
- [ ] \`GET /students/{id}/history\` returns list of past training plans
- [ ] History is ordered chronologically
- [ ] Current plan is not included in history

## 🔗 Related Issues
- Depends on #24, #12

## 🏷️ Labels
\`api\` \`progression\` \`history\`" > /dev/null
echo "✅ Issue #25 created"

echo ""
echo "🎉 All done! 5 milestones and 25 issues created successfully."
echo "👉 https://github.com/$OWNER/$REPO/issues"
