#!/usr/bin/env bash
docker run -d --name quartz-demo-postgres \
  -e POSTGRES_USER=postgresuser \
  -e POSTGRES_PASSWORD=postgrespassword \
  -e POSTGRES_DB=brontofundus_quartz_demo \
  -p 5432:5432 \
  postgres:15.3-alpine
#  -e PGDATA=/var/lib/postgresql/data/pgdata \
