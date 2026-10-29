# spring-boot-testcontainers-demo1

[![Maven Build](https://github.com/hendisantika/spring-boot-testcontainers-demo1/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-testcontainers-demo1/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot)
![Testcontainers](https://img.shields.io/badge/Testcontainers-2.x-2C3E50)

A Spring Boot demo that routes notifications between messaging systems and tests the whole flow against
real infrastructure using [Testcontainers](https://testcontainers.com/).

## How it works

```
ActiveMQ (queue: jms.events) ──► NotificationJMSListener ──► NotificationRouter ──┬──► PostgreSQL (notification table)
                                                                                  └──► RabbitMQ (testcontainers.test.exchange)
```

1. A JSON message such as `{"message": "hello"}` arrives on the ActiveMQ queue `jms.events`.
2. `NotificationJMSListener` deserializes it and hands a `Notification` to the router.
3. `NotificationRouter` stores the notification in PostgreSQL and forwards it to RabbitMQ.
4. `GET /` renders the stored notifications with Thymeleaf.

## Tech stack

| Area      | Technology                                                              |
|-----------|-------------------------------------------------------------------------|
| Runtime   | Java 25, Spring Boot 4.1 (Web MVC, Data JPA, AMQP, JMS, Thymeleaf)      |
| Messaging | ActiveMQ Classic (inbound), RabbitMQ (outbound)                         |
| Database  | PostgreSQL with Flyway migrations (`src/main/resources/db/migration`)   |
| JSON      | Jackson 3                                                               |
| Testing   | JUnit 5, Testcontainers 2 (PostgreSQL, RabbitMQ, ActiveMQ, Selenium)    |

## Prerequisites

- JDK 25
- Docker (Docker Desktop, OrbStack, Colima, …): Testcontainers starts all infrastructure for the tests
- Maven 3.8+ or the bundled wrapper (`./mvnw`)

## Running the tests

```bash
./mvnw clean verify
```

Testcontainers starts these containers for you:

| Test                                         | Containers                                         |
|----------------------------------------------|----------------------------------------------------|
| `NotificationRepositoryTest` (`@DataJpaTest`) | PostgreSQL                                         |
| `SpringBootTestcontainersDemo1ApplicationTests` | PostgreSQL, ActiveMQ, RabbitMQ                   |
| `RoutingTest`                                | PostgreSQL, ActiveMQ, RabbitMQ (end-to-end flow)   |
| `UITest`                                     | PostgreSQL, ActiveMQ, RabbitMQ, Selenium Chromium  |

`UITest` uses the multi-arch `selenium/standalone-chromium` image, so it also runs on Apple Silicon. The app is
exposed to the browser container through `host.testcontainers.internal`, which works on Linux CI runners as well
as on Docker Desktop.

`UITest#shouldFailThisTestUsingTheRemoteDriverAndGenerateAVideoRecording` is `@Disabled` because it fails on
purpose. Remove `@Disabled` to see Testcontainers save a VNC recording of the failing test to `./target`.

## Running the application

The app needs PostgreSQL, RabbitMQ and ActiveMQ. Start them with Docker:

```bash
docker run -d --name postgres -p 5432:5432 -e POSTGRES_DB=testC -e POSTGRES_PASSWORD=hendi34 postgres:latest
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
docker run -d --name activemq -p 61616:61616 -p 8161:8161 apache/activemq-classic:latest
```

Then run:

```bash
./mvnw spring-boot:run
```

Open <http://localhost:8080> to see the stored notifications. Connection settings are in
`src/main/resources/application.properties`.

## CI/CD

[`.github/workflows/maven.yml`](.github/workflows/maven.yml) runs on pushes to `main`, on pull requests, and on
manual dispatch:

- builds and tests on Temurin JDK 25, using the Docker engine on the GitHub-hosted runner for Testcontainers
- uploads surefire reports and any VNC recordings when the build fails
- auto-merges Dependabot **patch/minor** updates (squash) only after the build job has passed; major updates
  are left for manual review

## Dependency updates

[`.github/dependabot.yml`](.github/dependabot.yml) keeps dependencies current:

| Ecosystem      | Schedule                    | Notes                                                                 |
|----------------|-----------------------------|-----------------------------------------------------------------------|
| Maven          | daily, 05:00 Asia/Jakarta   | minor/patch grouped into one PR; Spring Boot parent majors ignored    |
| GitHub Actions | weekly (Monday)             | all action updates grouped into one PR                                |
| Docker         | weekly (Monday)             | base image of the ActiveMQ `Dockerfile`                               |

Dependabot commits follow Conventional Commits (`build(deps)`, `ci(deps)`, `build(docker)`) and are labelled
`dependencies`.

## Author

**Hendi Santika**

- Email: hendisantika@gmail.com
- Telegram: [@hendisantika34](https://t.me/hendisantika34)
