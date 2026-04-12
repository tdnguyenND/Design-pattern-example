# Factory Method Pattern

## 1) Generic overview
Factory Method is a **creational pattern** that defines an interface for creating an object while letting subclasses decide which concrete class to instantiate.

### Intent
- Separate object creation from object usage.
- Allow extension of product creation without changing client logic.

### When to use
- A workflow depends on an abstract product, not a concrete class.
- Different product variants are selected by subtype.

### Pros
- Reduces coupling to concrete implementations.
- Supports Open/Closed principle for adding new products.
- Keeps creation logic in one dedicated place.

### Cons
- Introduces additional class hierarchy.
- Can be overkill for very simple creation logic.

### Generic structure
- `Product` interface/abstract type.
- `ConcreteProductA/B` implementations.
- `Creator` abstract class declaring factory method.
- `ConcreteCreatorA/B` overriding factory method.

## 2) Concrete use case in this repository
**Use case:** sending notifications through different channels (Email, SMS) while keeping sending flow channel-agnostic.

## 3) Example code walkthrough (`Example.java`)
- `Notification` is the product interface.
- `EmailNotification` and `SmsNotification` are concrete products.
- `NotificationCreator` declares `createNotification()` and exposes `send(...)` workflow.
- `EmailCreator` and `SmsCreator` choose which product is created.
- `main` demonstrates sending through both creators.
