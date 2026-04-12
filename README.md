# Design Pattern Examples (Java)

This repository is a curated set of Java examples for common software design patterns.

## Structure

Each pattern lives in its own folder under `patterns/` and always contains:
- `README.md`: explains the problem, how the pattern works, key roles, and example flow.
- `Example.java`: a runnable example with a `main` method matching the documentation.

## Included patterns

### Creational
- Singleton (`patterns/singleton`)
- Factory Method (`patterns/factory-method`)
- Builder (`patterns/builder`)

### Structural
- Adapter (`patterns/adapter`)
- Decorator (`patterns/decorator`)
- Composite (`patterns/composite`)

### Behavioral
- Strategy (`patterns/strategy`)
- Observer (`patterns/observer`)

## Run one example

Example with Strategy:

```bash
javac patterns/strategy/Example.java
java -cp patterns/strategy Example
```

## Run all examples

```bash
for p in patterns/*; do
  javac "$p/Example.java" && java -cp "$p" Example
  echo "-----"
done
```
