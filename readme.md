# Quartz Cluster Demo

## Features of this Demo

* enable cluster mode for Quartz Jobs in Spring Boot



## Source of Quartz Database Script for Postgres

**Quartz Postgres Scripts**

    quartz-2.3.2.jar!/org/quartz/impl/jdbcjobstore/tables_postgres.sql

## Running the Demo

### Start Single Instance (Development)

If you just run the [QuartzDemoApplication.kt](src%2Fmain%2Fkotlin%2Fch%2Fbrontofundus%2Fdemos%2Fquartz%2FQuartzDemoApplication.kt) Spring-Boot Application you need to start the database first, using [run-postgres.sh](run-postgres.sh)

Open [localhost](http://localhost:8080/) to view the state of the application.

### Start Cluster Demo with Docker-Compose

The [docker-compose.yaml](docker-compose.yaml) allows you to spin up the database and 3 instances of the quartz-demo application.

You need to build the image first `mvn cerify`

and then use `docker-compose up -d` to start the cluster.

You can browse each instance by itself or open [/frames](http://localhost:18080/frames) which will create a page with 3 iframes showing each instance side-by-side.


## Quartz Cron Help

- http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-06.html
- http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html

## Cron Expressions zum Klicken
- https://www.freeformatter.com/cron-expression-generator-quartz.html

