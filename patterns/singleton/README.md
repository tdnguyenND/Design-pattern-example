# Singleton Pattern

## 1) Generic overview
Singleton is a **creational pattern** that ensures a class has exactly one instance and provides a global access point to it.

### Intent
- Guarantee single shared state for cross-cutting configuration/services.
- Centralize object lifecycle for one-instance components.

### When to use
- App configuration holder.
- Shared cache/registry.
- One logical service instance in a process.

### Pros
- Controlled single instance.
- Easy global access.
- Can avoid repeated expensive initialization.

### Cons
- Hidden global state can hurt testability.
- Potentially abused as a substitute for proper dependency injection.
- Thread-safety must be considered for lazy variants.

### Generic structure
- Private constructor.
- Private static field holding the instance.
- Public static accessor `getInstance()`.

## 2) Concrete use case in this repository
**Use case:** a central application config object (`appName`, `environment`) shared throughout the app.

## 3) Example code walkthrough (`Example.java`)
- `AppConfig` has:
  - private constructor,
  - `private static final AppConfig INSTANCE` (eager initialization),
  - public `getInstance()` accessor,
  - read-only getters.
- `main` obtains two references and verifies they point to the same object.
