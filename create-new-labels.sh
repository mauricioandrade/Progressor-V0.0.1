#!/bin/bash

OWNER="mauricioandrade"
REPO="Progressor-V0.0.1"

echo "🏷️ Creating new labels for $OWNER/$REPO..."
echo ""

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="docs" \
  --field color="0075ca" \
  --field description="Documentation related tasks" > /dev/null
echo "✅ Label 'docs' created"

gh api repos/$OWNER/$REPO/labels \
  --method POST \
  --field name="security" \
  --field color="b60205" \
  --field description="Security and authentication" > /dev/null
echo "✅ Label 'security' created"

echo ""
echo "🎉 New labels created successfully."
echo "👉 https://github.com/$OWNER/$REPO/labels"
