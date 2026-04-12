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

### Cons
- Adds more classes/interfaces.
- Clients must understand which strategy to choose.

### Generic structure
- `Strategy`: common algorithm interface.
- `ConcreteStrategyA/B/...`: implementations.
- `Context`: holds one strategy and delegates execution to it.

## 2) Concrete use case in this repository
**Use case:** shipping fee calculation in an e-commerce flow.

- Standard delivery
- Express delivery
- Same-day delivery

Instead of embedding all formulas in one class, each formula is implemented as a separate strategy.

## 3) Example code walkthrough (`Example.java`)
- `ShippingStrategy` defines `calculateFee(double weightKg)`.
- `StandardShipping`, `ExpressShipping`, `SameDayShipping` implement different fee formulas.
- `ShippingCalculator` is the context with:
  - constructor injection for initial strategy,
  - `setStrategy(...)` for runtime switching,
  - `calculate(...)` delegating to the current strategy.
- `main` demonstrates switching strategy for the same order weight and prints different results.
