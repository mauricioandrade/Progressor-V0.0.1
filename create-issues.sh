#!/bin/bash

# =============================================================================
# Progressor V0.0.1 — GitHub Issues/Milestones/Labels Setup Script
# Run: bash create-issues.sh
# Requires: gh CLI authenticated (gh auth login)
# =============================================================================

REPO="mauricioandrade/Progressor-V0.0.1"

echo "🚀 Starting Progressor GitHub setup..."
echo "📦 Repository: $REPO"
echo ""

# =============================================================================
# LABELS
# =============================================================================
echo "🏷️  Creating labels..."

gh label create "backend"      --repo "$REPO" --description "Backend related tasks"              --color "0075ca" --force
gh label create "frontend"     --repo "$REPO" --description "Frontend related tasks"             --color "7057ff" --force
gh label create "security"     --repo "$REPO" --description "Security and authentication"        --color "e4e669" --force
gh label create "entity"       --repo "$REPO" --description "JPA entities"                       --color "0e8a16" --force
gh label create "repository"   --repo "$REPO" --description "Repository layer"                   --color "006b75" --force
gh label create "service"      --repo "$REPO" --description "Service layer"                      --color "1d76db" --force
gh label create "controller"   --repo "$REPO" --description "Controller layer"                   --color "0052cc" --force
gh label create "api"          --repo "$REPO" --description "API endpoints"                      --color "bfd4f2" --force
gh label create "refactor"     --repo "$REPO" --description "Code refactoring"                   --color "fef2c0" --force
gh label create "architecture" --repo "$REPO" --description "Architecture decisions"             --color "c5def5" --force
gh label create "validation"   --repo "$REPO" --description "Input validation"                   --color "f9d0c4" --force
gh label create "fix"          --repo "$REPO" --description "Bug fixes"                          --color "d93f0b" --force
gh label create "docs"         --repo "$REPO" --description "Documentation related tasks"        --color "0075ca" --force
gh label create "setup"        --repo "$REPO" --description "Project setup and initialization"   --color "e4e669" --force
gh label create "infra"        --repo "$REPO" --description "Infrastructure tasks"               --color "c2e0c6" --force
gh label create "docker"       --repo "$REPO" --description "Docker and container related"       --color "0e8a16" --force
gh label create "config"       --repo "$REPO" --description "Configuration files"                --color "bfd4f2" --force

echo "✅ Labels created."
echo ""

# =============================================================================
# MILESTONES
# =============================================================================
echo "🗺️  Creating milestones..."

gh api repos/$REPO/milestones --method POST --field title="M10 - Refactor: Clean Architecture Migration"  --field description="♻️ Migrate all legacy MVC packages (controller/service/repository/dto) into the Clean Architecture structure (domain/application/infrastructure/presentation)." --field state="open"
gh api repos/$REPO/milestones --method POST --field title="M11 - Auth: User–Domain Binding"               --field description="🔐 Link the authenticated User entity to their domain profile (Student or PersonalTrainer) so the system knows who is logged in." --field state="open"
gh api repos/$REPO/milestones --method POST --field title="M12 - Student Module"                          --field description="🎓 Full student self-service: view and update own profile via authenticated endpoints." --field state="open"
gh api repos/$REPO/milestones --method POST --field title="M13 - Personal Trainer Module"                 --field description="🏋️ Full trainer profile management and student supervision relationship." --field state="open"
gh api repos/$REPO/milestones --method POST --field title="M14 - Training Plans Module"                   --field description="📋 Rewrite training plans with role-based rules and self-service for students and trainers." --field state="open"
gh api repos/$REPO/milestones --method POST --field title="M15 - Measurements Module"                     --field description="📏 Core feature for body measurement tracking, recordable by both students and trainers." --field state="open"
gh api repos/$REPO/milestones --method POST --field title="M16 - Frontend"                                --field description="🎨 Separate React + Vite frontend project consuming the Progressor API, following the established dark design system." --field state="open"

echo "✅ Milestones created."
echo ""

# =============================================================================
# FETCH MILESTONE NUMBERS
# =============================================================================
echo "🔍 Fetching milestone numbers..."

M10=$(gh api repos/$REPO/milestones --jq '.[] | select(.title | startswith("M10")) | .number')
M11=$(gh api repos/$REPO/milestones --jq '.[] | select(.title | startswith("M11")) | .number')
M12=$(gh api repos/$REPO/milestones --jq '.[] | select(.title | startswith("M12")) | .number')
M13=$(gh api repos/$REPO/milestones --jq '.[] | select(.title | startswith("M13")) | .number')
M14=$(gh api repos/$REPO/milestones --jq '.[] | select(.title | startswith("M14")) | .number')
M15=$(gh api repos/$REPO/milestones --jq '.[] | select(.title | startswith("M15")) | .number')
M16=$(gh api repos/$REPO/milestones --jq '.[] | select(.title | startswith("M16")) | .number')

echo "  M10 → #$M10 | M11 → #$M11 | M12 → #$M12 | M13 → #$M13 | M14 → #$M14 | M15 → #$M15 | M16 → #$M16"
echo ""

# =============================================================================
# ISSUES — M10
# =============================================================================
echo "📝 Creating M10 issues..."

gh issue create --repo "$REPO" \
  --title "[M10] Migrate package structure to Clean Architecture" \
  --milestone "$M10" \
  --label "refactor,backend,architecture" \
  --body "## ♻️ Overview

The project currently has two coexisting structures: the legacy MVC layout (\`controller/\`, \`service/\`, \`repository/\`, \`dto/\`) and the Clean Architecture layout (\`domain/\`, \`application/\`, \`infrastructure/\`, \`presentation/\`) introduced in M9. This issue covers reorganizing all existing classes into the correct layers.

## Target structure

\`\`\`
domain/student/        ← Student.java
domain/trainer/        ← PersonalTrainer.java
domain/training/       ← TrainingPlan.java
domain/person/         ← Person.java
domain/shared/         ← Goal.java, TrainingLevel.java, Progressable.java
application/student/   ← StudentService, DTOs, Mapper
application/trainer/   ← PersonalTrainerService, DTOs, Mapper
application/training/  ← TrainingPlanService, DTOs, Mapper
infrastructure/persistence/ ← all Repositories
presentation/student/  ← StudentController
presentation/trainer/  ← PersonalTrainerController
presentation/training/ ← TrainingPlanController
\`\`\`

## ✅ Acceptance criteria

- [ ] All packages follow the Clean Architecture structure
- [ ] No classes remain in \`controller/\`, \`service/\`, \`repository/\`, or \`dto/\`
- [ ] Application compiles and all existing endpoints respond correctly after migration
- [ ] Import statements updated across all files"

gh issue create --repo "$REPO" \
  --title "[M10] Create domain-specific exceptions to replace generic RuntimeException" \
  --milestone "$M10" \
  --label "refactor,backend,architecture" \
  --body "## 🚨 Overview

Services currently throw \`RuntimeException(\"Student not found with id: \" + id)\` and similar. These should be typed exceptions that the \`GlobalExceptionHandler\` can catch specifically and return the correct HTTP status.

## ✅ Acceptance criteria

- [ ] \`StudentNotFoundException\` created in \`domain/student/\`
- [ ] \`TrainerNotFoundException\` created in \`domain/trainer/\`
- [ ] \`TrainingPlanNotFoundException\` created in \`domain/training/\`
- [ ] \`GlobalExceptionHandler\` updated to handle each with HTTP 404
- [ ] String-matching logic removed from \`GlobalExceptionHandler\`"

gh issue create --repo "$REPO" \
  --title "[M10] Add Bean Validation to all request records" \
  --milestone "$M10" \
  --label "refactor,backend,validation" \
  --body "## ✅ Overview

Request records (\`StudentRequest\`, \`TrainerRequest\`, \`TrainingPlanRequest\`, \`RegisterRequest\`, \`LoginRequest\`) have no input validation. Invalid or null data reaches the database and causes unhandled exceptions.

## ✅ Acceptance criteria

- [ ] \`spring-boot-starter-validation\` confirmed in \`pom.xml\`
- [ ] \`@NotBlank\`, \`@NotNull\`, \`@Positive\` applied to all request records
- [ ] \`@Valid\` added to all \`@RequestBody\` parameters in controllers
- [ ] \`GlobalExceptionHandler\` handles \`MethodArgumentNotValidException\` with 400 and field-level error messages"

gh issue create --repo "$REPO" \
  --title "[M10] Move response mapping out of controllers into dedicated mappers" \
  --milestone "$M10" \
  --label "refactor,backend,architecture" \
  --body "## 🗺️ Overview

Controllers currently contain \`toResponse()\` private methods that map domain entities to DTOs. This logic should live in mapper classes within the \`application\` layer.

## ✅ Acceptance criteria

- [ ] \`StudentMapper\` created in \`application/student/\`
- [ ] \`TrainerMapper\` created in \`application/trainer/\`
- [ ] \`TrainingPlanMapper\` created in \`application/training/\`
- [ ] All \`toResponse()\` methods removed from controllers
- [ ] Controllers delegate mapping to their respective mapper"

gh issue create --repo "$REPO" \
  --title "[M10] Fix misplaced @Transactional annotation in StudentController" \
  --milestone "$M10" \
  --label "fix,backend" \
  --body "## 🐛 Overview

\`StudentController.getHistory()\` is annotated with \`@Transactional\`. Transaction management should be handled exclusively in the service layer, not in controllers.

## ✅ Acceptance criteria

- [ ] \`@Transactional\` removed from \`StudentController.getHistory()\`
- [ ] \`@Transactional\` confirmed on \`StudentService.findById()\` to handle lazy loading correctly"

gh issue create --repo "$REPO" \
  --title "[M10] Make all service classes final" \
  --milestone "$M10" \
  --label "refactor,backend" \
  --body "## 🔒 Overview

\`PersonalTrainerService\`, \`StudentService\`, and \`TrainingPlanService\` are not declared \`final\`, which is inconsistent with the pattern used in \`AuthService\`, \`JwtService\`, and \`UserDetailsServiceImpl\`.

## ✅ Acceptance criteria

- [ ] \`PersonalTrainerService\` declared \`final\`
- [ ] \`StudentService\` declared \`final\`
- [ ] \`TrainingPlanService\` declared \`final\`"

gh issue create --repo "$REPO" \
  --title "[M10] Update Role enum to include STUDENT and TRAINER" \
  --milestone "$M10" \
  --label "refactor,backend,security" \
  --body "## 👤 Overview

The \`Role\` enum currently only has \`ADMIN\` and \`USER\`. To support role-based access control in future milestones, roles must map to actual domain concepts.

## ✅ Acceptance criteria

- [ ] \`Role\` enum values updated to: \`ADMIN\`, \`STUDENT\`, \`TRAINER\`
- [ ] \`USER\` role removed
- [ ] \`AuthService.register()\` updated — default role becomes \`STUDENT\`
- [ ] Security config and any role references updated to reflect new values"

echo "✅ M10 issues created."
echo ""

# =============================================================================
# ISSUES — M11
# =============================================================================
echo "📝 Creating M11 issues..."

gh issue create --repo "$REPO" \
  --title "[M11] Link User entity to Student via OneToOne relationship" \
  --milestone "$M11" \
  --label "backend,entity,security" \
  --body "## 🔗 Overview

\`User\` (authentication) and \`Student\` (domain) are currently unrelated entities. A \`@OneToOne\` relationship must be established so that when a user authenticates, their student profile can be retrieved.

## ✅ Acceptance criteria

- [ ] \`Student\` has a \`@OneToOne\` field referencing \`User\`
- [ ] Foreign key \`user_id\` present in the \`students\` table
- [ ] \`StudentRepository\` has \`findByUserId(UUID userId)\`
- [ ] Application starts and Hibernate creates the schema correctly"

gh issue create --repo "$REPO" \
  --title "[M11] Link User entity to PersonalTrainer via OneToOne relationship" \
  --milestone "$M11" \
  --label "backend,entity,security" \
  --body "## 🔗 Overview

\`User\` (authentication) and \`PersonalTrainer\` (domain) are currently unrelated entities. A \`@OneToOne\` relationship must be established for trainers, mirroring the student binding.

## ✅ Acceptance criteria

- [ ] \`PersonalTrainer\` has a \`@OneToOne\` field referencing \`User\`
- [ ] Foreign key \`user_id\` present in the \`personal_trainers\` table
- [ ] \`PersonalTrainerRepository\` has \`findByUserId(UUID userId)\`"

gh issue create --repo "$REPO" \
  --title "[M11] Update register endpoint to accept role and auto-create domain profile" \
  --milestone "$M11" \
  --label "backend,security,service" \
  --body "## 📝 Overview

When a user registers, the system should automatically create the corresponding domain profile (\`Student\` or \`PersonalTrainer\`) based on the provided role, eliminating the need for separate creation calls.

## ✅ Acceptance criteria

- [ ] \`RegisterRequest\` includes \`role\` (STUDENT | TRAINER), \`name\`, \`phone\`
- [ ] \`AuthService.register()\` creates \`Student\` or \`PersonalTrainer\` after saving \`User\`
- [ ] Both entities share the same \`user_id\` reference
- [ ] Returns \`AuthResponse\` with JWT token as before"

gh issue create --repo "$REPO" \
  --title "[M11] Implement GET /api/users/me endpoint" \
  --milestone "$M11" \
  --label "backend,api,controller" \
  --body "## 👤 Overview

The existing \`GET /api/users/me\` only returns a plain string. It should return the authenticated user's full profile including their domain data (student or trainer profile).

## ✅ Acceptance criteria

- [ ] Returns \`UserProfileResponse\` with: \`id\`, \`email\`, \`role\`, and either \`studentProfile\` or \`trainerProfile\` depending on role
- [ ] Uses \`Authentication\` from Spring Security context to identify the caller
- [ ] Returns 401 if not authenticated"

echo "✅ M11 issues created."
echo ""

# =============================================================================
# ISSUES — M12
# =============================================================================
echo "📝 Creating M12 issues..."

gh issue create --repo "$REPO" \
  --title "[M12] Implement GET /students/me endpoint" \
  --milestone "$M12" \
  --label "backend,api,controller" \
  --body "## 👤 Overview

An authenticated student should be able to retrieve their own profile without needing to know their database ID. The endpoint resolves the student from the authenticated user.

## ✅ Acceptance criteria

- [ ] \`GET /students/me\` returns \`StudentResponse\` for the authenticated student
- [ ] Uses authenticated user's ID to find the linked \`Student\`
- [ ] Returns 403 if called by a \`TRAINER\` role"

gh issue create --repo "$REPO" \
  --title "[M12] Implement PUT /students/me endpoint" \
  --milestone "$M12" \
  --label "backend,api,controller" \
  --body "## ✏️ Overview

An authenticated student should be able to update their own profile data such as name, phone, age, weight, height, and goal.

## ✅ Acceptance criteria

- [ ] \`PUT /students/me\` accepts \`StudentUpdateRequest\` with all fields optional
- [ ] Only updates fields that are present in the request (partial update)
- [ ] Returns updated \`StudentResponse\`
- [ ] Returns 403 if called by a \`TRAINER\` role"

echo "✅ M12 issues created."
echo ""

# =============================================================================
# ISSUES — M13
# =============================================================================
echo "📝 Creating M13 issues..."

gh issue create --repo "$REPO" \
  --title "[M13] Implement GET /trainers/me endpoint" \
  --milestone "$M13" \
  --label "backend,api,controller" \
  --body "## 👤 Overview

An authenticated trainer should be able to retrieve their own profile, mirroring the student self-service endpoint.

## ✅ Acceptance criteria

- [ ] \`GET /trainers/me\` returns \`TrainerResponse\` for the authenticated trainer
- [ ] Returns 403 if called by a \`STUDENT\` role"

gh issue create --repo "$REPO" \
  --title "[M13] Implement trainer–student supervision relationship" \
  --milestone "$M13" \
  --label "backend,entity,service" \
  --body "## 🤝 Overview

A trainer should be able to manage which students are under their supervision. This requires a relationship between \`PersonalTrainer\` and \`Student\` and dedicated endpoints to manage it.

## ✅ Acceptance criteria

- [ ] \`Student\` has an optional \`@ManyToOne\` to \`PersonalTrainer\`
- [ ] \`POST /trainers/me/students/{studentId}\` — links a student to the authenticated trainer
- [ ] \`DELETE /trainers/me/students/{studentId}\` — unlinks a student
- [ ] \`GET /trainers/me/students\` — lists all supervised students
- [ ] Returns 404 if student not found, 403 if caller is not a trainer"

echo "✅ M13 issues created."
echo ""

# =============================================================================
# ISSUES — M14
# =============================================================================
echo "📝 Creating M14 issues..."

gh issue create --repo "$REPO" \
  --title "[M14] Enforce role-based access on training plan creation" \
  --milestone "$M14" \
  --label "backend,security,service" \
  --body "## 🔒 Overview

Any user can currently create training plans. Access must be restricted: trainers create plans for students; students can also create their own personal plans via a dedicated endpoint.

## ✅ Acceptance criteria

- [ ] \`POST /training-plans\` requires \`TRAINER\` role
- [ ] \`POST /training-plans/me\` — student creates their own plan, requires \`STUDENT\` role
- [ ] Returns 403 for incorrect role on each endpoint"

gh issue create --repo "$REPO" \
  --title "[M14] Implement GET /training-plans/me/current" \
  --milestone "$M14" \
  --label "backend,api,controller" \
  --body "## 📋 Overview

A student should be able to retrieve their currently active training plan without knowing the plan ID.

## ✅ Acceptance criteria

- [ ] Returns the authenticated student's \`currentTrainingPlan\` as \`TrainingPlanResponse\`
- [ ] Returns 404 if no plan is currently assigned
- [ ] Requires \`STUDENT\` role"

gh issue create --repo "$REPO" \
  --title "[M14] Implement GET /training-plans/me/history" \
  --milestone "$M14" \
  --label "backend,api,controller" \
  --body "## 📜 Overview

A student should be able to view the full history of training plans they have been through.

## ✅ Acceptance criteria

- [ ] Returns \`List<TrainingPlanResponse>\` from the student's \`trainingHistory\`
- [ ] Returns empty list if no history exists
- [ ] Requires \`STUDENT\` role"

gh issue create --repo "$REPO" \
  --title "[M14] Implement PATCH /students/me/progress" \
  --milestone "$M14" \
  --label "backend,api,service" \
  --body "## ⬆️ Overview

A student should be able to trigger their own training level progression, automatically moving from BEGINNER → INTERMEDIATE → ADVANCED and receiving an appropriate training plan.

## ✅ Acceptance criteria

- [ ] Calls \`student.evolve()\` on the authenticated student
- [ ] Automatically assigns a plan matching the new level if one exists
- [ ] Returns 400 if student is already at \`ADVANCED\`
- [ ] Requires \`STUDENT\` role"

echo "✅ M14 issues created."
echo ""

# =============================================================================
# ISSUES — M15
# =============================================================================
echo "📝 Creating M15 issues..."

gh issue create --repo "$REPO" \
  --title "[M15] Create Measurement entity and repository" \
  --milestone "$M15" \
  --label "backend,entity,repository" \
  --body "## 📐 Overview

The \`Measurement\` entity represents a physical assessment snapshot for a student at a given point in time. It is the core of the measurements feature.

## Fields

| Field | Type | Required |
|---|---|---|
| id | UUID | ✅ |
| studentId | Long | ✅ |
| recordedAt | LocalDate | ✅ |
| weightKg | Double | ✅ |
| heightCm | Double | ✅ |
| bodyFatPercent | Double | ❌ |
| muscleMassPercent | Double | ❌ |
| waistCm | Double | ❌ |
| hipCm | Double | ❌ |
| chestCm | Double | ❌ |
| armCm | Double | ❌ |
| thighCm | Double | ❌ |

## ✅ Acceptance criteria

- [ ] \`Measurement\` entity created in \`domain/measurement/\`
- [ ] \`MeasurementRepository\` created in \`infrastructure/persistence/\`
- [ ] \`findByStudentIdOrderByRecordedAtDesc(Long studentId)\` query method present
- [ ] Hibernate creates the \`measurements\` table correctly on startup"

gh issue create --repo "$REPO" \
  --title "[M15] Implement POST /measurements — student records own measurements" \
  --milestone "$M15" \
  --label "backend,api,controller" \
  --body "## ➕ Overview

An authenticated student should be able to record a new measurement for themselves, capturing their current physical stats.

## ✅ Acceptance criteria

- [ ] \`POST /measurements\` accepts \`MeasurementRequest\` with all physical fields
- [ ] Associates the measurement with the authenticated student automatically
- [ ] Returns \`MeasurementResponse\` with \`201 Created\`
- [ ] Requires \`STUDENT\` role"

gh issue create --repo "$REPO" \
  --title "[M15] Implement GET /measurements — student views own measurement history" \
  --milestone "$M15" \
  --label "backend,api,controller" \
  --body "## 📊 Overview

A student should be able to retrieve their full measurement history, ordered from most recent to oldest.

## ✅ Acceptance criteria

- [ ] Returns \`List<MeasurementResponse>\` ordered by \`recordedAt\` descending
- [ ] Returns empty list if no measurements have been recorded
- [ ] Requires \`STUDENT\` role"

gh issue create --repo "$REPO" \
  --title "[M15] Implement trainer measurement endpoints for supervised students" \
  --milestone "$M15" \
  --label "backend,api,controller" \
  --body "## 📋 Overview

A trainer should be able to record and view measurements for students under their supervision, enabling professional assessment tracking.

## ✅ Acceptance criteria

- [ ] \`POST /trainers/me/students/{studentId}/measurements\` — records a measurement for a supervised student
- [ ] \`GET /trainers/me/students/{studentId}/measurements\` — retrieves full measurement history of a supervised student
- [ ] Returns 403 if the student is not linked to the authenticated trainer
- [ ] Requires \`TRAINER\` role"

gh issue create --repo "$REPO" \
  --title "[M15] Implement GET /measurements/evolution endpoint" \
  --milestone "$M15" \
  --label "backend,api,service" \
  --body "## 📈 Overview

Returns the delta between the student's first and latest measurement, enabling the frontend to display evolution metrics such as weight lost, body fat reduction, and muscle gain.

## ✅ Acceptance criteria

- [ ] Returns \`EvolutionResponse\` with: first measurement date, latest measurement date, and delta for each numeric field (e.g. \`weightDelta\`, \`bodyFatDelta\`, \`muscleMassDelta\`)
- [ ] Returns 404 if fewer than 2 measurements exist for the student
- [ ] Requires \`STUDENT\` role"

echo "✅ M15 issues created."
echo ""

# =============================================================================
# ISSUES — M16
# =============================================================================
echo "📝 Creating M16 issues..."

gh issue create --repo "$REPO" \
  --title "[M16] Setup React + Vite project with Tailwind and Axios" \
  --milestone "$M16" \
  --label "frontend,setup" \
  --body "## ⚙️ Overview

Initialize the frontend project with the chosen tech stack, configured and ready for feature development.

## ✅ Acceptance criteria

- [ ] Project created with \`npm create vite@latest\` using React + TypeScript template
- [ ] Tailwind CSS configured
- [ ] Axios installed with base URL configured via \`.env\`
- [ ] React Router configured with routes: \`/login\`, \`/register\`, \`/dashboard\`, \`/measurements\`, \`/training\`, \`/progress\`
- [ ] Project runs with \`npm run dev\`"

gh issue create --repo "$REPO" \
  --title "[M16] Implement Login and Register pages" \
  --milestone "$M16" \
  --label "frontend,security" \
  --body "## 🔐 Overview

Authentication screens that serve as the application entry point. Must follow the established dark design system.

## ✅ Acceptance criteria

- [ ] Login page: email + password form, calls \`POST /auth/login\`, stores JWT in memory
- [ ] Register page: name, email, password, role selector (Student / Trainer), calls \`POST /auth/register\`
- [ ] Redirects to \`/dashboard\` on success
- [ ] Shows error message on invalid credentials
- [ ] Uses Syne + DM Sans fonts, \`#0d0f14\` background, \`#00e5a0\` accent color"

gh issue create --repo "$REPO" \
  --title "[M16] Implement Dashboard page" \
  --milestone "$M16" \
  --label "frontend" \
  --body "## 🏠 Overview

The main landing page after login, showing a summary of the authenticated user's profile and recent activity.

## ✅ Acceptance criteria

- [ ] Calls \`GET /api/users/me\` to display user name and role
- [ ] Shows current training plan name (if any)
- [ ] Shows most recent measurement date (if any)
- [ ] Sidebar navigation active on Dashboard item"

gh issue create --repo "$REPO" \
  --title "[M16] Implement Measurements page" \
  --milestone "$M16" \
  --label "frontend" \
  --body "## 📏 Overview

The body measurement tracking page, based on the provided UI reference with dark design system.

## ✅ Acceptance criteria

- [ ] Calls \`GET /measurements\` to populate history
- [ ] Stat cards showing: current weight, height, body fat %, muscle mass %
- [ ] Line chart showing weight evolution over time
- [ ] Measurement list with latest values and delta badges (green positive / red negative)
- [ ] Modal form for new measurement registration, calls \`POST /measurements\`
- [ ] Follows the dark design system from the UI reference"

gh issue create --repo "$REPO" \
  --title "[M16] Implement Training Plans page" \
  --milestone "$M16" \
  --label "frontend" \
  --body "## 🏋️ Overview

Training plan management page with different views for students and trainers.

## ✅ Acceptance criteria

- [ ] Student view: current plan with exercises list via \`GET /training-plans/me/current\`
- [ ] Student can view history via \`GET /training-plans/me/history\`
- [ ] Trainer view: list of all plans, can create a new plan
- [ ] Follows the established dark design system"

gh issue create --repo "$REPO" \
  --title "[M16] Implement Evolution page with charts" \
  --milestone "$M16" \
  --label "frontend" \
  --body "## 📈 Overview

Visual progress tracking page showing the student's physical evolution over time with charts.

## ✅ Acceptance criteria

- [ ] Calls \`GET /measurements/evolution\` for delta summary data
- [ ] Line charts for weight, body fat %, and muscle mass % over time
- [ ] Training level badge: BEGINNER / INTERMEDIATE / ADVANCED
- [ ] Follows the established dark design system"

echo ""
echo "✅ M16 issues created."
echo ""
echo "=============================================="
echo "🎉 All done! Summary:"
echo "  🏷️  17 labels created"
echo "  🗺️   7 milestones created (M10–M16)"
echo "  📝  29 issues created"
echo "=============================================="
echo "👉 Check your issues at: https://github.com/$REPO/issues"
