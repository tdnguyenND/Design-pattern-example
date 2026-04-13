# Prototype Pattern

## 1) Generic overview
Prototype is a **creational pattern** that creates new objects by copying an existing instance (the prototype) instead of constructing from scratch.

### Intent
- Avoid costly creation by cloning pre-configured objects.
- Create new objects without knowing their exact class.
- Use a registry of prototypes to produce objects on demand.

### When to use
- Object creation is expensive (complex setup, database calls, etc.).
- You need many similar objects with minor variations.
- You want to avoid a large hierarchy of factory classes.

### Pros
- Avoids expensive initialization.
- Hides concrete classes from the client.
- Can clone complex object graphs.

### Cons
- Deep cloning can be tricky (circular references, mutable shared state).
- Every class must implement clone properly.

### Generic structure

```
+-------------------+
|    Prototype      |
+-------------------+
| + clone()         |
+-------------------+
        ^
        |
+-------------------+       +-------------------+
| ConcretePrototype |       |    Registry       |
+-------------------+       +-------------------+
| + clone()         |<------| + register()      |
+-------------------+       | + create()        |
                            +-------------------+
```

- `Prototype`: declares the `clone()` method.
- `ConcretePrototype`: implements cloning via copy constructor or similar.
- `Registry` (optional): stores named prototypes and creates clones on demand.

### Related patterns
- **Abstract Factory**: can use prototypes instead of factory methods to create products.
- **Builder**: builds complex objects step by step; prototype copies them in one shot.
- **Memento**: both deal with object state, but memento captures state for undo, prototype copies for creation.

### Real-world examples in Java
- `Object.clone()` — built-in prototype support.
- `java.util.ArrayList(Collection)` — copy constructor.
- Spring bean scopes: `@Scope("prototype")` creates a new instance per request.

## 2) Concrete use case in this repository

**Use case:** a document template system where pre-configured templates are cloned and customized.

| Component | Role |
|-----------|------|
| `DocumentPrototype` | Prototype interface |
| `Document` | Concrete prototype with title, content, tags |
| `DocumentRegistry` | Registry of named templates |

### Deep copy guarantee
Cloned documents have independent `tags` lists — modifying the clone does not affect the original.

## 3) Example code walkthrough (`Example.java`)

### Demo 1: Basic cloning
- Create an original document, clone it, modify the clone.
- Show that the original remains unchanged (deep copy).

### Demo 2: Template registry
- Register "memo" and "report" templates.
- Create multiple documents from templates, customize each.
- Show that all clones are independent.

### Expected output

```
=== Cloning a document ===
Original:
  Title:   Project Proposal
  Content: We propose to build a new platform...
  Tags:    [proposal, draft]

Clone (modified):
  Title:   Project Proposal (Copy)
  Content: We propose to build a new platform...
  Tags:    [proposal, draft, copy]

Original (unchanged):
  Title:   Project Proposal
  Content: We propose to build a new platform...
  Tags:    [proposal, draft]

=== Using a template registry ===
memo1:
  Title:   Q1 Memo
  Content: To: Team
From: Manager

Please review Q1 results.
  Tags:    [memo, internal]

memo2:
  Title:   Holiday Memo
  Content: To: Team
From: Manager

[body]
  Tags:    [memo, internal]

report:
  Title:   Annual Report 2025
  Content: Summary:

[details]
  Tags:    [report]
```
