#!/bin/bash

OWNER="mauricioandrade"
REPO="Progressor-V0.0.1"

echo "🏷️ Creating labels for $OWNER/$REPO..."
echo ""

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="setup" \
  --field color="0075ca" \
  --field description="Project setup and initialization" > /dev/null
echo "✅ Label 'setup' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="backend" \
  --field color="e4e669" \
  --field description="Backend related tasks" > /dev/null
echo "✅ Label 'backend' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="config" \
  --field color="cfd3d7" \
  --field description="Configuration files" > /dev/null
echo "✅ Label 'config' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="architecture" \
  --field color="d93f0b" \
  --field description="Project structure and architecture" > /dev/null
echo "✅ Label 'architecture' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="docker" \
  --field color="0052cc" \
  --field description="Docker and container related" > /dev/null
echo "✅ Label 'docker' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="infra" \
  --field color="5319e7" \
  --field description="Infrastructure tasks" > /dev/null
echo "✅ Label 'infra' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="domain" \
  --field color="006b75" \
  --field description="Domain entities and business rules" > /dev/null
echo "✅ Label 'domain' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="enum" \
  --field color="bfd4f2" \
  --field description="Enum types" > /dev/null
echo "✅ Label 'enum' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="entity" \
  --field color="bfe5bf" \
  --field description="JPA entities" > /dev/null
echo "✅ Label 'entity' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="jpa" \
  --field color="fef2c0" \
  --field description="JPA mappings and relationships" > /dev/null
echo "✅ Label 'jpa' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="progression" \
  --field color="e99695" \
  --field description="Progression system" > /dev/null
echo "✅ Label 'progression' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="repository" \
  --field color="c5def5" \
  --field description="Repository layer" > /dev/null
echo "✅ Label 'repository' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="service" \
  --field color="f9d0c4" \
  --field description="Service layer" > /dev/null
echo "✅ Label 'service' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="dto" \
  --field color="d4c5f9" \
  --field description="Data Transfer Objects" > /dev/null
echo "✅ Label 'dto' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="api" \
  --field color="0e8a16" \
  --field description="REST API endpoints" > /dev/null
echo "✅ Label 'api' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="controller" \
  --field color="1d76db" \
  --field description="Controller layer" > /dev/null
echo "✅ Label 'controller' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="interface" \
  --field color="b60205" \
  --field description="Java interfaces" > /dev/null
echo "✅ Label 'interface' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="history" \
  --field color="fbca04" \
  --field description="History tracking" > /dev/null
echo "✅ Label 'history' created"

echo ""
echo "🎉 All done! 18 labels created successfully."
echo "👉 https://github.com/$OWNER/$REPO/labels"
