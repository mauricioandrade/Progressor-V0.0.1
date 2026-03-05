#!/bin/bash

OWNER="mauricioandrade"
REPO="Progressor-V0.0.1"

echo "📝 Updating issue descriptions for Progressor..."
echo ""

# ─────────────────────────────────────────────
# M6
# ─────────────────────────────────────────────

gh issue edit 27 --repo $OWNER/$REPO --body "## 🩺 Description
Expose health and info endpoints via \`management\` configuration in \`application.yml\`.

## ✅ Acceptance Criteria
- [ ] \`/actuator/health\` returns 200 with DB status
- [ ] \`/actuator/info\` is accessible
- [ ] \`show-details: always\` configured

## 🔗 Dependencies
- Add Actuator dependency"
echo "✅ #27 updated"

# ─────────────────────────────────────────────
# M7
# ─────────────────────────────────────────────

gh issue edit 28 --repo $OWNER/$REPO --body "## 📦 Description
Create a multi-stage \`Dockerfile\` to build and run the Progressor application in a container.

## ✅ Acceptance Criteria
- [ ] Multi-stage build (build stage + runtime stage)
- [ ] Uses Java 25 base image
- [ ] Application starts correctly inside the container
- [ ] Image size is optimized

## 📝 Notes
Use \`eclipse-temurin:25-jdk\` for build and \`eclipse-temurin:25-jre\` for runtime.

## 🔗 Dependencies
None"
echo "✅ #28 updated"

gh issue edit 29 --repo $OWNER/$REPO --body "## 📦 Description
Update \`docker-compose.yml\` to include the Spring Boot application alongside the PostgreSQL container, so the full stack can be started with a single command.

## ✅ Acceptance Criteria
- [ ] Application container defined in docker-compose
- [ ] App depends on the PostgreSQL container (healthcheck)
- [ ] Environment variables configured for DB connection
- [ ] \`docker compose up\` starts both containers successfully
- [ ] App connects to DB automatically on startup

## 🔗 Dependencies
- Create Dockerfile"
echo "✅ #29 updated"

gh issue edit 30 --repo $OWNER/$REPO --body "## 📦 Description
Add a healthcheck to the PostgreSQL container so the application only starts after the database is ready.

## ✅ Acceptance Criteria
- [ ] PostgreSQL service has a healthcheck configured
- [ ] Application service uses \`depends_on: condition: service_healthy\`
- [ ] No connection errors on startup

## 🔗 Dependencies
- Update docker-compose"
echo "✅ #30 updated"

# ─────────────────────────────────────────────
# M8
# ─────────────────────────────────────────────

gh issue edit 31 --repo $OWNER/$REPO --body "## 📖 Description
Add the \`springdoc-openapi-starter-webmvc-ui\` dependency to enable Swagger UI and API documentation.

## ✅ Acceptance Criteria
- [ ] Dependency added to pom.xml
- [ ] Swagger UI accessible at \`/swagger-ui.html\`
- [ ] OpenAPI JSON accessible at \`/v3/api-docs\`

## 📝 Notes
Use SpringDoc — it is compatible with Spring Boot 3+/4+.

## 🔗 Dependencies
None"
echo "✅ #31 updated"

gh issue edit 32 --repo $OWNER/$REPO --body "## 📖 Description
Add \`@Operation\`, \`@ApiResponse\`, and \`@Tag\` annotations to \`StudentController\` to document all endpoints in Swagger UI.

## ✅ Acceptance Criteria
- [ ] All endpoints documented with summary and description
- [ ] Response codes documented (200, 201, 400, 404)
- [ ] DTOs documented with \`@Schema\` annotations

## 🔗 Dependencies
- Add SpringDoc dependency"
echo "✅ #32 updated"

gh issue edit 33 --repo $OWNER/$REPO --body "## 📖 Description
Add \`@Operation\`, \`@ApiResponse\`, and \`@Tag\` annotations to \`PersonalTrainerController\` to document all endpoints in Swagger UI.

## ✅ Acceptance Criteria
- [ ] All endpoints documented with summary and description
- [ ] Response codes documented (200, 201, 400, 404)
- [ ] DTOs documented with \`@Schema\` annotations

## 🔗 Dependencies
- Add SpringDoc dependency"
echo "✅ #33 updated"

gh issue edit 34 --repo $OWNER/$REPO --body "## 📖 Description
Add \`@Operation\`, \`@ApiResponse\`, and \`@Tag\` annotations to \`TrainingPlanController\` to document all endpoints in Swagger UI.

## ✅ Acceptance Criteria
- [ ] All endpoints documented with summary and description
- [ ] Response codes documented (200, 201, 400, 404)
- [ ] DTOs documented with \`@Schema\` annotations

## 🔗 Dependencies
- Add SpringDoc dependency"
echo "✅ #34 updated"

# ─────────────────────────────────────────────
# M9
# ─────────────────────────────────────────────

gh issue edit 35 --repo $OWNER/$REPO --body "## 🔐 Description
Add \`spring-boot-starter-security\` and a JWT library to \`pom.xml\` to enable authentication and authorization.

## ✅ Acceptance Criteria
- [ ] Spring Security dependency added
- [ ] JWT library added (\`io.jsonwebtoken:jjwt-api\`)
- [ ] Application still starts correctly

## 📝 Notes
Suggested JWT library: \`io.jsonwebtoken:jjwt-api\`

## 🔗 Dependencies
None"
echo "✅ #35 updated"

gh issue edit 36 --repo $OWNER/$REPO --body "## 🔐 Description
Create a \`User\` entity with \`username\`, \`password\`, and \`role\` fields, along with its JPA repository.

## ✅ Acceptance Criteria
- [ ] User entity created with appropriate fields
- [ ] UserRepository created extending JpaRepository
- [ ] Table created in the database on startup

## 🔗 Dependencies
- Add Spring Security dependency"
echo "✅ #36 updated"

gh issue edit 37 --repo $OWNER/$REPO --body "## 🔐 Description
Create a \`JwtService\` responsible for generating and validating JWT tokens.

## ✅ Acceptance Criteria
- [ ] Token generation with expiration time
- [ ] Token validation and claims extraction
- [ ] Secret key configured via application.yml

## 🔗 Dependencies
- Add JWT dependency"
echo "✅ #37 updated"

gh issue edit 38 --repo $OWNER/$REPO --body "## 🔐 Description
Create an \`AuthController\` with \`POST /auth/register\` and \`POST /auth/login\` endpoints to handle user registration and authentication.

## ✅ Acceptance Criteria
- [ ] \`POST /auth/register\` creates a new user with encoded password
- [ ] \`POST /auth/login\` returns a JWT token on valid credentials
- [ ] Invalid credentials return 401

## 🔗 Dependencies
- Implement JWT service"
echo "✅ #38 updated"

gh issue edit 39 --repo $OWNER/$REPO --body "## 🔐 Description
Configure \`SecurityFilterChain\` to protect API endpoints and allow public access to auth routes, Swagger and Actuator.

## ✅ Acceptance Criteria
- [ ] \`/auth/**\` routes are public
- [ ] \`/actuator/health\` is public
- [ ] \`/swagger-ui/**\` and \`/v3/api-docs/**\` are public
- [ ] All other routes require a valid JWT token
- [ ] JWT filter added to the filter chain

## 🔗 Dependencies
- Create auth endpoints
- Implement JWT service"
echo "✅ #39 updated"

echo ""
echo "🎉 All 13 issues updated successfully!"
echo "👉 https://github.com/$OWNER/$REPO/issues"
