# Strategy Pattern

## 1) Generic overview
Strategy is a **behavioral pattern** that defines a family of algorithms, encapsulates each algorithm, and makes them interchangeable at runtime.

### Intent
- Isolate algorithm variations behind a shared interface.
- Remove `if/else` or `switch` logic from the main workflow.
- Allow runtime selection of behavior.

### When to use
- You have multiple variants of one behavior (pricing, sorting, validation, discount rules).
- You want to add new algorithms without modifying existing business flow.
- You need to switch behavior dynamically.

### Pros
- Open/Closed friendly: add new strategy classes without changing context.
- Better testability: each strategy can be unit tested in isolation.
- Cleaner business flow in the context class.
- Java 8+: lambdas can be used for simple strategies (single-method interface).

### Cons
- Adds more classes/interfaces.
- Clients must understand which strategy to choose.

### Generic structure

```
+-------------+         +------------------+
|   Context   |-------->|    Strategy      |
+-------------+         +------------------+
| - strategy  |         | + algorithm()    |
| + execute() |         +------------------+
+-------------+                 ^
                                |
                    +-----------+-----------+
                    |                       |
             ConcreteStrategyA      ConcreteStrategyB
```

- `Strategy`: common algorithm interface.
- `ConcreteStrategyA/B/...`: implementations.
- `Context`: holds one strategy and delegates execution to it.

### Related patterns
- **State**: same structure, but state transitions happen internally while strategy is chosen by the client.
- **Template Method**: uses inheritance to vary part of an algorithm; strategy uses composition.
- **Factory Method**: can be used to select the appropriate strategy.

### Real-world examples in Java
- `Comparator<T>` — strategy for sorting.
- `java.util.Arrays.sort(T[], Comparator)` — context accepting a strategy.
- `javax.servlet.Filter` — strategy for request processing.
- Spring `@Qualifier` + interface — inject the appropriate strategy.

## 2) Concrete use case in this repository

**Use case:** calculating shipping fees in an e-commerce system.

| Strategy | Formula | Use case |
|----------|---------|----------|
| `StandardShipping` | 15,000 + weight x 5,000 | Normal orders |
| `ExpressShipping` | 30,000 + weight x 8,000 | Fast delivery |
| `SameDayShipping` | 50,000 + weight x 12,000 | Same-day delivery |
| Lambda `freeShipping` | 0 | Free shipping promo |
| Lambda `flatRate` | 20,000 | Flat rate shipping |

## 3) Example code walkthrough (`Example.java`)

### Demo 1: Comparing strategies
Same order (2.5 kg), switching strategies to see different fees.

### Demo 2: Selecting strategy based on conditions
Based on weight, automatically selects the appropriate strategy — instead of putting if/else inside business logic.

### Demo 3: Lambda strategy
Since `SimpleShipping` is a functional interface (1 abstract method), lambdas can be used instead of creating a class. Note: `ShippingStrategy` has 2 methods (`calculateFee` + `getName`), so it cannot be used with lambdas directly — the demo uses a separate `SimpleShipping` interface to illustrate the concept.

### Expected output

```
=== Comparing shipping fees for the same order ===
  [Standard] Order ORD-001 (2.5 kg): 27,500 VND
  [Express] Order ORD-001 (2.5 kg): 50,000 VND
  [Same-day] Order ORD-001 (2.5 kg): 80,000 VND

=== Selecting strategy based on conditions ===
  [Standard] Order AUTO (0.5 kg): 17,500 VND
  [Express] Order AUTO (3.0 kg): 54,000 VND
  [Same-day] Order AUTO (10.0 kg): 170,000 VND

=== Strategy with lambda (Java 8+) ===
  Free shipping (2.5 kg): 0 VND
  Flat rate (2.5 kg):     20,000 VND
```
