# Factory Method Pattern

## 1) Generic overview
Factory Method is a **creational pattern** that defines an interface for creating an object while letting subclasses decide which concrete class to instantiate.

### Intent
- Separate object creation from object usage.
- Allow extension of product creation without changing client logic.

### When to use
- A workflow depends on an abstract product, not a concrete class.
- Different product variants are selected by subtype or configuration.
- You need to add new product types without modifying existing code.

### Pros
- Reduces coupling to concrete implementations.
- Supports Open/Closed principle for adding new products.
- Keeps creation logic in one dedicated place.

### Cons
- Introduces additional class hierarchy.
- Can be overkill for very simple creation logic.

### Generic structure

```
+---------------------+         +-------------------+
|      Creator        |         |     Product       |
+---------------------+         +-------------------+
| + factoryMethod()   |-------->| + operation()     |
| + templateMethod()  |         +-------------------+
+---------------------+                 ^
         ^                              |
         |                    +---------+---------+
+---------------------+      |                   |
| ConcreteCreatorA    |   ProductA           ProductB
+---------------------+
| + factoryMethod()   |
+---------------------+
```

- `Product` interface/abstract type.
- `ConcreteProductA/B` implementations.
- `Creator` abstract class declaring factory method + template workflow.
- `ConcreteCreatorA/B` overriding factory method.

### Related patterns
- **Abstract Factory**: factory method is often used to implement abstract factory.
- **Template Method**: factory method is often called from a template method.
- **Strategy**: both use composition, but strategy swaps algorithms while factory method swaps object creation.

### Real-world examples in Java
- `Collection.iterator()` — each collection decides which Iterator to return.
- `NumberFormat.getInstance(Locale)` — returns format matching the locale.
- Spring `BeanFactory.getBean()`.

## 2) Concrete use case in this repository

**Use case:** a notification system that sends messages through multiple channels (Email, SMS, Push) while keeping the sending workflow channel-agnostic.

| Creator | Product | Channel |
|---------|---------|---------|
| `EmailCreator` | `EmailNotification` | Email |
| `SmsCreator` | `SmsNotification` | SMS |
| `PushCreator` | `PushNotification` | Push |

Adding a new channel (e.g. Slack, Telegram) only requires adding 1 Product + 1 Creator — no changes to existing code.

## 3) Example code walkthrough (`Example.java`)

### Components
- `Notification` is the product interface with `send(recipient, message)` and `getChannel()`.
- `EmailNotification`, `SmsNotification`, `PushNotification` are concrete products.
- `NotificationCreator` is the abstract creator with:
  - `createNotification()`: factory method (abstract).
  - `notify(...)`: template method containing shared workflow (prepare -> send -> log).
- `EmailCreator`, `SmsCreator`, `PushCreator` override the factory method.

### Factory registry
- `getCreator(String channel)` selects the appropriate creator at runtime from config/input.
- The demo shows both approaches: direct invocation and selection from string.

### Expected output

```
=== Calling each Creator directly ===
Preparing to send via: Email
[EMAIL -> user@example.com] Welcome to PatternHub!
Sent successfully!

Preparing to send via: SMS
[SMS -> 0901234567] Your OTP is 482910
Sent successfully!

=== Selecting Creator from config (runtime) ===
Preparing to send via: Email
[EMAIL -> admin] System maintenance at 2:00 AM
Sent successfully!

Preparing to send via: SMS
[SMS -> admin] System maintenance at 2:00 AM
Sent successfully!

Preparing to send via: Push
[PUSH -> admin] System maintenance at 2:00 AM
Sent successfully!
```
