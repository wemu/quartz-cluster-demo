# Quartz Cluster Demo

## Features of this Demo

* show usage of Spring Boot and the Quartz-Scheduler
* enable cluster mode for Quartz Jobs in Spring Boot
* Provide docker-compose file to start a realistic cluster
* use PostgreSQL to demonstrate JDBC Store and concurrent job execution


## Source of Quartz Database Script for Postgres

This demo uses Flyway to populate the database. Including the Quartz required tables mean you need to make Flyway aware of those scripts. In the demo we copied it. It's source:

**Quartz Postgres Scripts**

    quartz-2.3.2.jar!/org/quartz/impl/jdbcjobstore/tables_postgres.sql

## Running the Demo

### Start Single Instance (Development)

If you just run the [QuartzDemoApplication.kt](src/main/kotlin/ch/brontofundus/demos/quartz/QuartzDemoApplication.kt) Spring-Boot Application you need to start the database first, using [run-postgres.sh](run-postgres.sh)

Open [localhost](http://localhost:8080/) to view the state of the application.

### Start Cluster Demo with Docker-Compose

The [docker-compose.yaml](docker-compose.yaml) allows you to spin up the database and 3 instances of the quartz-demo application.

You need to build the image first `mvn cerify`

and then use `docker-compose up -d` to start the cluster.

You can browse each instance by itself or open [/frames](http://localhost:18080/frames) which will create a page with 3 iframes showing each instance side-by-side.


## Quartz Cron Help

- http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-06.html
- http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html
- [Cron Expressions Generator](https://www.freeformatter.com/cron-expression-generator-quartz.html)

## Learn and Observe

Every instance of the application will start with two properties:

```
INST_NAME: "anyNumer-somename"
INST_WAITMILLIS: 25000
```

INST_NAME is used to identify the instance of the application in the cluster - it is used for logging and creating the Job keys and information.

INST_WAITMILLIS is used to allow every instance of the application to execute the Jobs slower than other nodes - if you want to play around with overlapping job execution times.

Watch out for the concurrent jobs (the might just run several times, even on the same node - especially if the jobs overlap - meaning they execute faster than they complete, so the next execution starts before the last one finished). A different behaviour is shown with the non-concurrent variation of the job (look out for the `DisallowConcurrentExecution` annotation!)

You can still use the Scheduled annotation from spring - but be aware that all instances will run them (might still be useful for some use-cases).

If you pause the scheduler, this will only affect that one instance of the application. The others still run.

To stop all processing you can stop all the triggers - this will affect the whole cluster.

JobListener only fire on the instance that was executing the job as well.
