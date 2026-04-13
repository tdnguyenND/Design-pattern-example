# Builder Pattern

## 1) Generic overview
Builder is a **creational pattern** that separates the construction of a complex object from its representation, allowing step-by-step creation.

### Intent
- Avoid telescoping constructors with many optional fields.
- Improve readability and safety of object construction.
- Validate state before creating the object.

### When to use
- Object has many optional parameters.
- You need fluent, expressive construction code.
- You want immutable end objects with controlled creation.
- You need validation logic before object creation.

### Pros
- Readable construction flow.
- Works well with immutable objects.
- Reduces constructor overload complexity.
- Can validate before creating the object.

### Cons
- More boilerplate (builder class and methods).
- Might be unnecessary for simple objects.

### Generic structure

```
+------------------+          +-------------------+
|    Director      |          |     Builder       |
+------------------+          +-------------------+
| + construct()    |--------->| + stepA()         |
+------------------+          | + stepB()         |
                              | + build(): Product|
                              +-------------------+
                                       |
                              +-------------------+
                              |    Product        |
                              +-------------------+
                              | - fieldA          |
                              | - fieldB          |
                              +-------------------+
```

- Target class with private/final fields and private constructor.
- Nested `Builder` class collecting parameters with fluent setters.
- `build()` method to validate and create target instance.
- Optional `Director` to provide pre-configured builder templates.

### Related patterns
- **Abstract Factory**: also creates objects, but factory creates a family of objects while builder constructs one complex object.
- **Singleton**: builder can be used inside singleton for complex configuration.

### Real-world examples in Java
- `StringBuilder` / `StringBuffer`
- `Stream.Builder`
- `HttpRequest.newBuilder()` (Java 11+)
- Lombok `@Builder`

## 2) Concrete use case in this repository

**Use case:** creating a `Report` object with many optional fields.

| Field | Required? | Default |
|-------|-----------|---------|
| `title` | Yes | (must be passed to constructor) |
| `author` | No | `"Unknown"` |
| `content` | No | `""` |
| `includeChart` | No | `false` |
| `confidential` | No | `false` |
| `format` | No | `"PDF"` |

### Validation rules
- `title` must not be blank.
- Confidential reports cannot be exported as HTML.

### Director
- `ReportDirector` provides pre-configured templates (monthly finance, public newsletter).
- Callers can customize further before calling `build()`.

## 3) Example code walkthrough (`Example.java`)

### Builder pattern
- `Report` has a private constructor that only accepts `Builder`.
- `Report.Builder` has a required field (`title` via constructor) and optional fields (fluent setters).
- `build()` validates before creating the object.

### Director pattern
- `ReportDirector` returns a pre-configured `Report.Builder`.
- Client can add/override fields before calling `build()`.

### Demo flow
1. Create a simple report with only the title (uses default values).
2. Create a detailed report with all fields.
3. Use Director to create reports from templates.
4. Demo validation when violating a business rule.

### Expected output

```
=== Building manually with Builder ===
--- Report ---
Title:        Quick Status
Author:       Unknown
Content:      (empty)
Chart:        No
Confidential: No
Format:       PDF

--- Report ---
Title:        Q1 Revenue Analysis
Author:       Data Team
Content:      Revenue increased 15% compared to Q4 last year
Chart:        Yes
Confidential: Yes
Format:       PDF

=== Using Director (pre-configured templates) ===
--- Report ---
Title:        Monthly Financial Report
Author:       Accounting Team
Content:      Total revenue: $2.5M
Chart:        Yes
Confidential: Yes
Format:       PDF

--- Report ---
Title:        Internal Newsletter
Author:       Communications Team
Content:      Congrats to the dev team for completing sprint 42!
Chart:        No
Confidential: No
Format:       HTML

=== Validation in build() ===
Error: Confidential reports cannot be exported as HTML
```
