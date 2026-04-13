# Design Pattern Examples (Java)

This repository is a curated set of Java examples for common software design patterns.

## Structure

Each pattern lives in its own folder at the project root and always contains:
- `README.md`: explains the problem, how the pattern works, key roles, and example flow.
- `Example.java`: a runnable example with a `main` method matching the documentation.

## Included patterns

### Creational
- Singleton (`singleton`)
- Factory Method (`factory-method`)
- Builder (`builder`)

### Structural
- Adapter (`adapter`)
- Decorator (`decorator`)
- Composite (`composite`)

### Behavioral
- Strategy (`strategy`)
- Observer (`observer`)

## Run one example

Example with Strategy:

```bash
javac strategy/Example.java
java -cp strategy Example
```

## Run all examples

```bash
for p in adapter builder composite decorator factory-method observer singleton strategy; do
  javac "$p/Example.java" && java -cp "$p" Example
  echo "-----"
done
```
