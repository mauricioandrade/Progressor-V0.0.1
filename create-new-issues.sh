#!/bin/bash

OWNER="mauricioandrade"
REPO="Progressor-V0.0.1"

echo "🚀 Creating milestones and issues for Progressor..."
echo ""

# ─────────────────────────────────────────────
# MILESTONES
# ─────────────────────────────────────────────

echo "📌 Creating milestones..."

M6=$(gh api repos/$OWNER/$REPO/milestones --method POST \
  --field title="🩺 M6 — Health Check" \
  --field description="Spring Boot Actuator setup and monitoring endpoints" \
  --jq '.number')
echo "✅ M6 created (#$M6)"

M7=$(gh api repos/$OWNER/$REPO/milestones --method POST \
  --field title="📦 M7 — Docker Complete" \
  --field description="Dockerfile and full docker-compose for the entire stack" \
  --jq '.number')
echo "✅ M7 created (#$M7)"

M8=$(gh api repos/$OWNER/$REPO/milestones --method POST \
  --field title="📖 M8 — API Documentation" \
  --field description="Swagger / OpenAPI documentation" \
  --jq '.number')
echo "✅ M8 created (#$M8)"

M9=$(gh api repos/$OWNER/$REPO/milestones --method POST \
  --field title="🔐 M9 — Authentication" \
  --field description="Spring Security with JWT authentication" \
  --jq '.number')
echo "✅ M9 created (#$M9)"

echo ""

# ─────────────────────────────────────────────
# M6 — Health Check (already done)
# ─────────────────────────────────────────────

echo "🩺 Creating M6 issues..."

gh issue create \
  --title "[M6] Add Spring Boot Actuator dependency" \
  --body "## Description
Add the \`spring-boot-starter-actuator\` dependency to \`pom.xml\`.

## Acceptance Criteria
- [ ] Actuator dependency added to pom.xml
- [ ] Application starts without errors

## Dependencies
None" \
  --milestone "$M6" \
  --label "setup,backend"
echo "✅ M6 #1 created"

gh issue create \
  --title "[M6] Configure Actuator endpoints in application.yml" \
  --body "## Description
Expose health and info endpoints via \`management\` configuration in \`application.yml\`.

## Acceptance Criteria
- [ ] \`/actuator/health\` returns 200 with DB status
- [ ] \`/actuator/info\` is accessible
- [ ] \`show-details: always\` configured

## Dependencies
- Add Actuator dependency" \
  --milestone "$M6" \
  --label "config,backend"
echo "✅ M6 #2 created"

echo ""

# ─────────────────────────────────────────────
# M7 — Docker Complete
# ─────────────────────────────────────────────

echo "📦 Creating M7 issues..."

gh issue create \
  --title "[M7] Create Dockerfile for the Spring Boot application" \
  --body "## Description
Create a multi-stage \`Dockerfile\` to build and run the Progressor application in a container.

## Acceptance Criteria
- [ ] Multi-stage build (build stage + runtime stage)
- [ ] Uses Java 25 base image
- [ ] Application starts correctly inside the container
- [ ] Image size is optimized

## Notes
Use \`eclipse-temurin:25-jdk\` for build and \`eclipse-temurin:25-jre\` for runtime." \
  --milestone "$M7" \
  --label "docker,infra"
echo "✅ M7 #1 created"

gh issue create \
  --title "[M7] Update docker-compose to include the application container" \
  --body "## Description
Update \`docker-compose.yml\` to include the Spring Boot application alongside the PostgreSQL container, so the full stack can be started with a single command.

## Acceptance Criteria
- [ ] Application container defined in docker-compose
- [ ] App depends on the PostgreSQL container (healthcheck)
- [ ] Environment variables configured for DB connection
- [ ] \`docker compose up\` starts both containers successfully
- [ ] App connects to DB automatically on startup

## Dependencies
- Create Dockerfile" \
  --milestone "$M7" \
  --label "docker,infra,config"
echo "✅ M7 #2 created"

gh issue create \
  --title "[M7] Add healthcheck to PostgreSQL service in docker-compose" \
  --body "## Description
Add a healthcheck to the PostgreSQL container so the application only starts after the database is ready.

## Acceptance Criteria
- [ ] PostgreSQL service has a healthcheck configured
- [ ] Application service uses \`depends_on: condition: service_healthy\`
- [ ] No connection errors on startup

## Dependencies
- Update docker-compose" \
  --milestone "$M7" \
  --label "docker,infra"
echo "✅ M7 #3 created"

echo ""

# ─────────────────────────────────────────────
# M8 — API Documentation
# ─────────────────────────────────────────────

echo "📖 Creating M8 issues..."

gh issue create \
  --title "[M8] Add SpringDoc OpenAPI dependency" \
  --body "## Description
Add the \`springdoc-openapi-starter-webmvc-ui\` dependency to enable Swagger UI.

## Acceptance Criteria
- [ ] Dependency added to pom.xml
- [ ] Swagger UI accessible at \`/swagger-ui.html\`
- [ ] OpenAPI JSON accessible at \`/v3/api-docs\`

## Notes
Use SpringDoc — it is compatible with Spring Boot 3+/4+." \
  --milestone "$M8" \
  --label "setup,api,docs"
echo "✅ M8 #1 created"

gh issue create \
  --title "[M8] Document StudentController with OpenAPI annotations" \
  --body "## Description
Add \`@Operation\`, \`@ApiResponse\`, and \`@Tag\` annotations to \`StudentController\`.

## Acceptance Criteria
- [ ] All endpoints documented with summary and description
- [ ] Response codes documented (200, 201, 400, 404)
- [ ] DTOs documented with \`@Schema\` annotations

## Dependencies
- Add SpringDoc dependency" \
  --milestone "$M8" \
  --label "api,docs,controller"
echo "✅ M8 #2 created"

gh issue create \
  --title "[M8] Document PersonalTrainerController with OpenAPI annotations" \
  --body "## Description
Add \`@Operation\`, \`@ApiResponse\`, and \`@Tag\` annotations to \`PersonalTrainerController\`.

## Acceptance Criteria
- [ ] All endpoints documented
- [ ] Response codes documented
- [ ] DTOs documented

## Dependencies
- Add SpringDoc dependency" \
  --milestone "$M8" \
  --label "api,docs,controller"
echo "✅ M8 #3 created"

gh issue create \
  --title "[M8] Document TrainingPlanController with OpenAPI annotations" \
  --body "## Description
Add \`@Operation\`, \`@ApiResponse\`, and \`@Tag\` annotations to \`TrainingPlanController\`.

## Acceptance Criteria
- [ ] All endpoints documented
- [ ] Response codes documented
- [ ] DTOs documented

## Dependencies
- Add SpringDoc dependency" \
  --milestone "$M8" \
  --label "api,docs,controller"
echo "✅ M8 #4 created"

echo ""

# ─────────────────────────────────────────────
# M9 — Authentication
# ─────────────────────────────────────────────

echo "🔐 Creating M9 issues..."

gh issue create \
  --title "[M9] Add Spring Security and JWT dependencies" \
  --body "## Description
Add \`spring-boot-starter-security\` and a JWT library to \`pom.xml\`.

## Acceptance Criteria
- [ ] Spring Security dependency added
- [ ] JWT library added (e.g. \`jjwt\`)
- [ ] Application still starts correctly

## Notes
Suggested JWT library: \`io.jsonwebtoken:jjwt-api\`" \
  --milestone "$M9" \
  --label "setup,backend,security"
echo "✅ M9 #1 created"

gh issue create \
  --title "[M9] Create User entity and UserRepository" \
  --body "## Description
Create a \`User\` entity with \`username\`, \`password\`, and \`role\` fields, along with its JPA repository.

## Acceptance Criteria
- [ ] User entity created with appropriate fields
- [ ] UserRepository created extending JpaRepository
- [ ] Table created in the database on startup

## Dependencies
- Add Spring Security dependency" \
  --milestone "$M9" \
  --label "entity,security,repository"
echo "✅ M9 #2 created"

gh issue create \
  --title "[M9] Implement JWT token generation and validation" \
  --body "## Description
Create a \`JwtService\` responsible for generating and validating JWT tokens.

## Acceptance Criteria
- [ ] Token generation with expiration time
- [ ] Token validation and claims extraction
- [ ] Secret key configured via application.yml

## Dependencies
- Add JWT dependency" \
  --milestone "$M9" \
  --label "service,security"
echo "✅ M9 #3 created"

gh issue create \
  --title "[M9] Create authentication endpoints (register and login)" \
  --body "## Description
Create an \`AuthController\` with \`POST /auth/register\` and \`POST /auth/login\` endpoints.

## Acceptance Criteria
- [ ] \`POST /auth/register\` creates a new user with encoded password
- [ ] \`POST /auth/login\` returns a JWT token on valid credentials
- [ ] Invalid credentials return 401

## Dependencies
- Implement JWT service" \
  --milestone "$M9" \
  --label "controller,api,security"
echo "✅ M9 #4 created"

gh issue create \
  --title "[M9] Configure Spring Security filter chain" \
  --body "## Description
Configure \`SecurityFilterChain\` to protect API endpoints and allow public access to auth routes and Swagger.

## Acceptance Criteria
- [ ] \`/auth/**\` routes are public
- [ ] \`/actuator/health\` is public
- [ ] \`/swagger-ui/**\` and \`/v3/api-docs/**\` are public
- [ ] All other routes require a valid JWT token
- [ ] JWT filter added to the filter chain

## Dependencies
- Create auth endpoints
- Implement JWT service" \
  --milestone "$M9" \
  --label "config,security,backend"
echo "✅ M9 #5 created"

echo ""
echo "🎉 All done! Milestones M6–M9 and issues created successfully."
echo "👉 https://github.com/$OWNER/$REPO/issues"
