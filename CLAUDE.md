# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```powershell
# Run the application
.\gradlew.bat bootRun

# Build without running
.\gradlew.bat build

# Run all tests
.\gradlew.bat test

# Run a single test class
.\gradlew.bat test --tests "com.example.demo.DemoApplicationTests"

# Run a single test method
.\gradlew.bat test --tests "com.example.demo.DemoApplicationTests.contextLoads"

# Clean build artifacts
.\gradlew.bat clean
```

The app starts on **http://localhost:8080** by default.

## Tech Stack

- **Spring Boot 4.1.1** with Spring Web MVC (servlet-based REST)
- **Gradle** (wrapper at `gradlew.bat` — do not require a local Gradle install)
- **Java 17**
- **Lombok** for boilerplate reduction (`@Data`, `@Builder`, `@Slf4j`, etc.)
- **Spring Boot DevTools** — auto-restarts the app on class changes during `bootRun`

## Project Structure

```
src/main/java/com/example/demo/
    DemoApplication.java        # Entry point; @SpringBootApplication bootstraps everything

src/main/resources/
    application.properties      # App config (port, datasource, etc.)

src/test/java/com/example/demo/
    DemoApplicationTests.java   # Context load smoke test
```

## Architecture Notes

This is a **bare Spring Boot scaffold** — no controllers, services, or repositories exist yet. The conventional layer structure to follow when adding features:

- `controller/` — `@RestController` classes handling HTTP routes
- `service/` — business logic (`@Service`)
- `repository/` — data access (`@Repository`, Spring Data interfaces)
- `model/` or `entity/` — POJOs / JPA entities

`@SpringBootApplication` on `DemoApplication` enables component scanning for the entire `com.example.demo` package tree, so any `@Component`-annotated class placed in a subpackage is auto-detected.

## Key Dependency Notes

- Use `spring-boot-starter-webmvc` (already included) for REST controllers — do not add `spring-boot-starter-web` separately (it's the same artifact under a different alias in this version).
- Lombok annotations require the `annotationProcessor` to be on the compile classpath — already configured in `build.gradle`.
