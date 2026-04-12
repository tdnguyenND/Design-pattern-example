# Decorator Pattern

## 1) Generic overview
Decorator is a **structural pattern** that lets you attach additional responsibilities to objects dynamically by wrapping them.

### Intent
- Extend behavior without subclass explosion.
- Compose features at runtime.

### When to use
- You need many optional combinations of behavior.
- Inheritance would create too many subclasses.

### Pros
- Flexible runtime composition.
- Follows Open/Closed principle.
- Small reusable decorators.

### Cons
- Many small wrapper classes can make debugging harder.
- Object identity/type checks may be less straightforward.

### Generic structure
- `Component` interface.
- `ConcreteComponent` base behavior.
- `Decorator` base wrapper implementing `Component`.
- `ConcreteDecoratorA/B` adding behavior before/after delegation.

## 2) Concrete use case in this repository
**Use case:** drink customization where a base coffee can be enhanced with milk, caramel, and boba.

## 3) Example code walkthrough (`Example.java`)
- `Drink` is the component interface (`description`, `cost`).
- `Coffee` is the base component.
- `DrinkDecorator` stores wrapped `Drink`.
- `MilkDecorator`, `CaramelDecorator`, `BobaDecorator` increment description and price.
- `main` wraps coffee with multiple decorators and prints final output.
