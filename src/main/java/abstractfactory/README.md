# Abstract Factory Pattern

## 1) Generic overview
Abstract Factory is a **creational pattern** that provides an interface for creating families of related objects without specifying their concrete classes.

### Intent
- Create families of related objects that are designed to work together.
- Ensure consistency among products in the same family.
- Decouple client code from concrete product classes.

### When to use
- A system needs to be independent of how its products are created.
- A system should work with multiple families of products.
- You want to enforce that products from the same family are used together.

### Pros
- Products from the same factory are guaranteed to be compatible.
- Loose coupling between client and concrete products.
- Easy to swap entire product families.

### Cons
- Adding a new product type requires changes to the factory interface and all implementations.
- Can result in many classes for each product family.

### Generic structure

```
+-------------------+       +-------------------+
| AbstractFactory   |       | AbstractProductA  |
+-------------------+       +-------------------+
| + createProductA()|------>|                   |
| + createProductB()|       +-------------------+
+-------------------+              ^
        ^                          |
        |                  +-------+-------+
+-------------------+      |               |
| ConcreteFactory1  |   ProductA1      ProductA2
+-------------------+
| + createProductA()|
| + createProductB()|
+-------------------+
```

- `AbstractFactory`: declares creation methods for each product type.
- `ConcreteFactory`: creates products belonging to one family.
- `AbstractProduct`: interface for a product type.
- `ConcreteProduct`: implementation tied to a specific family.

### Related patterns
- **Factory Method**: abstract factory is often implemented with factory methods.
- **Builder**: can be combined with abstract factory for complex product construction.
- **Singleton**: factories are often singletons.

### Real-world examples in Java
- `javax.xml.parsers.DocumentBuilderFactory` — creates XML parsers.
- Swing Look & Feel — each L&F provides a family of UI components.
- Spring `BeanFactory` — creates families of beans based on configuration.

## 2) Concrete use case in this repository

**Use case:** a UI toolkit that supports Light and Dark themes. Each theme provides its own Button and Checkbox.

| Factory | Button | Checkbox |
|---------|--------|----------|
| `LightThemeFactory` | `LightButton` | `LightCheckbox` |
| `DarkThemeFactory` | `DarkButton` | `DarkCheckbox` |

The `Application` class only depends on `UIFactory` — it doesn't know which theme it's rendering.

## 3) Example code walkthrough (`Example.java`)

### Components
- `Button` and `Checkbox` are abstract product interfaces.
- `LightButton/DarkButton` and `LightCheckbox/DarkCheckbox` are concrete products.
- `UIFactory` is the abstract factory with `createButton()` and `createCheckbox()`.
- `LightThemeFactory` and `DarkThemeFactory` are concrete factories.
- `Application` is the client — receives a factory and builds its UI from it.

### Demo
1. Create an app with Light theme, then Dark theme.
2. Select theme from a string config at runtime.

### Expected output

```
=== Creating UI with Light theme ===
Rendering Light theme:
  [Light Button] white background, dark text
  [Light Checkbox] outlined style

=== Creating UI with Dark theme ===
Rendering Dark theme:
  [Dark Button] dark background, light text
  [Dark Checkbox] filled style

=== Selecting theme from config ===
Rendering Light theme:
  [Light Button] white background, dark text
  [Light Checkbox] outlined style

Rendering Dark theme:
  [Dark Button] dark background, light text
  [Dark Checkbox] filled style
```
