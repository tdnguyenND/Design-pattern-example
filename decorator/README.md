# Decorator Pattern

## 1) Generic overview
Decorator is a **structural pattern** that lets you attach additional responsibilities to objects dynamically by wrapping them.

### Intent
- Extend behavior without subclass explosion.
- Compose features at runtime.
- Allow flexible combinations instead of creating a class for every permutation.

### When to use
- You need many optional combinations of behavior.
- Inheritance would create too many subclasses.
- You want to add/remove behavior at runtime.

### Pros
- Flexible runtime composition.
- Follows Open/Closed principle.
- Small reusable decorators.
- The same decorator can be applied multiple times (e.g. double topping).

### Cons
- Many small wrapper classes can make debugging harder.
- Object identity/type checks may be less straightforward.
- Decorator order can affect the result.

### Generic structure

```
+-----------+
| Component |<-----------------------+
+-----------+                        |
| + operation() |                    |
+-----------+                        |
      ^                              |
      |                              |
+-----+------+          +-----------+----------+
| Concrete   |          |     Decorator        |
| Component  |          +----------------------+
+------------+          | - component: Component|
                        | + operation()         |
                        +----------------------+
                                 ^
                        +--------+--------+
                        |                 |
                  DecoratorA         DecoratorB
```

- `Component` interface.
- `ConcreteComponent` base behavior.
- `Decorator` base wrapper implementing `Component`, holding a reference to the wrapped component.
- `ConcreteDecoratorA/B` add behavior before/after delegation.

### Related patterns
- **Adapter**: also wraps objects, but adapter converts the interface rather than adding behavior.
- **Composite**: both use recursive composition, but composite organizes a tree while decorator adds features.
- **Strategy**: changes the "internals" of an object; decorator changes the "shell".

### Real-world examples in Java
- `BufferedInputStream(new FileInputStream(...))` — Java I/O streams.
- `Collections.unmodifiableList(list)` — adds immutability.
- `Collections.synchronizedList(list)` — adds thread-safety.

## 2) Concrete use case in this repository

**Use case:** a drink shop where each base drink can be enhanced with any number of toppings.

| Class | Role | Price |
|-------|------|-------|
| `Coffee` | Base drink | 30,000 |
| `GreenTea` | Base drink | 25,000 |
| `MilkDecorator` | Topping | +5,000 |
| `CaramelDecorator` | Topping | +7,000 |
| `BobaDecorator` | Topping | +8,000 |
| `WhipCreamDecorator` | Topping | +6,000 |

With inheritance: 2 bases x 4 toppings x all combinations = dozens of classes.
With decorator: only 6 classes, freely composable.

## 3) Example code walkthrough (`Example.java`)

### Demo 1: Step-by-step decoration
Shows how the price and description change after each layer of wrapping.

### Demo 2: Multiple combos
Creates various combos from 2 base drinks, demonstrating flexibility.

### Demo 3: Double topping
The same decorator (`MilkDecorator`) applied twice — decorators are not limited to one use.

### Expected output

```
=== Step-by-step decoration ===
  Base:        Coffee                                          30,000 VND
  +Milk:       Coffee + Milk                                   35,000 VND
  +Caramel:    Coffee + Milk + Caramel                         42,000 VND
  +Boba:       Coffee + Milk + Caramel + Boba                  50,000 VND

=== Multiple combos from 2 base drinks ===
  Combo 1:     Coffee + Milk + Whip Cream                      41,000 VND
  Combo 2:     Green Tea + Milk + Boba                         38,000 VND
  Full:        Coffee + Milk + Boba + Caramel + Whip Cream     56,000 VND

=== Double topping (same decorator twice) ===
  2x Milk:     Green Tea + Milk + Milk                         35,000 VND
```
