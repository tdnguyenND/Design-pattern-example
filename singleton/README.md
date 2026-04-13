# Singleton Pattern

## 1) Generic overview
Singleton is a **creational pattern** that ensures a class has exactly one instance and provides a global access point to it.

### Intent
- Guarantee single shared state for cross-cutting configuration/services.
- Centralize object lifecycle for one-instance components.

### When to use
- App configuration holder.
- Shared cache/registry.
- Database connection pool.
- Logger.

### Pros
- Controlled single instance.
- Easy global access.
- Can avoid repeated expensive initialization.

### Cons
- Hidden global state can hurt testability.
- Potentially abused as a substitute for proper dependency injection.
- Thread-safety must be considered for lazy variants.

### Generic structure

```
+---------------------------+
|        Singleton           |
+---------------------------+
| - instance: Singleton      |
| - Singleton()              |
+---------------------------+
| + getInstance(): Singleton |
| + operation()              |
+---------------------------+
```

- Private constructor.
- Private static field holding the instance.
- Public static accessor `getInstance()`.

### Related patterns
- **Factory Method**: can return a singleton instance.
- **Builder**: builder itself is often a singleton in framework code.

### Real-world examples in Java
- `java.lang.Runtime.getRuntime()`
- `java.awt.Desktop.getDesktop()`
- Spring beans are singletons by default (`@Scope("singleton")`)

## 2) Concrete use case in this repository

**Use case:** three common ways to implement Singleton:

| Variant | Class | Thread-safe? | When to use |
|---------|-------|-------------|-------------|
| Eager init | `AppConfig` | Yes (JVM guarantees) | Always needed, lightweight init |
| Lazy double-checked locking | `DatabaseConnection` | Yes (volatile + sync) | Expensive init, might not be used |
| Enum singleton | `Logger` | Yes (JVM guarantees) | Best practice per Effective Java |

## 3) Example code walkthrough (`Example.java`)

### Eager initialization (`AppConfig`)
- `private static final AppConfig INSTANCE` is created at class load time.
- Shared state (`requestCount`) is accessible from all callers of `getInstance()`.
- Demo: both `c1` and `c2` operate on the same counter.

### Lazy initialization (`DatabaseConnection`)
- `volatile` + double-checked locking ensures thread-safety without locking the whole method.
- Connection is only created on the first call to `getInstance()`.
- Demo: `db1.connect()` makes `db2.isConnected()` return `true`.

### Enum singleton (`Logger`)
- Cannot create new instances; serialize-safe and reflection-safe.
- Recommended by Joshua Bloch (Effective Java Item 3).

### Expected output

```
=== Eager Singleton (AppConfig) ===
App: PatternHub [production]
Total requests (via c1): 3
Total requests (via c2): 3
Same instance? true

=== Lazy Singleton (DatabaseConnection) ===
[DB] Initializing connection to: jdbc:mysql://localhost:3306/mydb
[DB] Connected successfully: jdbc:mysql://localhost:3306/mydb
db2 connected? true
Same instance? true

=== Enum Singleton (Logger) ===
[INFO] Application started
[ERROR] Something went wrong
Same instance? true
```
