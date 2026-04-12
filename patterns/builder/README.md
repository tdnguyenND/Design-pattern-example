# Builder Pattern

## 1) Generic overview
Builder is a **creational pattern** that separates the construction of a complex object from its representation, allowing step-by-step creation.

### Intent
- Avoid telescoping constructors with many optional fields.
- Improve readability and safety of object construction.

### When to use
- Object has many optional parameters.
- You need fluent, expressive construction code.
- You want immutable end objects with controlled creation.

### Pros
- Readable construction flow.
- Works well with immutable objects.
- Reduces constructor overload complexity.

### Cons
- More boilerplate (builder class and methods).
- Might be unnecessary for simple objects.

### Generic structure
- Target class with private/final fields.
- Nested or separate `Builder` class collecting parameters.
- `build()` method to create target instance.

## 2) Concrete use case in this repository
**Use case:** creating a `Report` object with optional flags (`includeChart`, `confidential`) and metadata (`title`, `author`).

## 3) Example code walkthrough (`Example.java`)
- `Report` is the immutable target object.
- `Report.Builder` provides fluent setters and `build()`.
- `main` demonstrates chained builder calls to create a monthly report.
