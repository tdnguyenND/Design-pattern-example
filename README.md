# Design Pattern Examples (Java)

This repository is a curated set of Java examples for common software design patterns.

## Prerequisites

- Java 17+

## Structure

```
src/main/java/
├── singleton/
├── factorymethod/
├── builder/
├── adapter/
├── decorator/
├── composite/
├── strategy/
└── observer/
```

Each pattern package contains:
- `Example.java`: a runnable example with a `main` method.
- `README.md`: explains the problem, how the pattern works, key roles, and example flow.

Smoke tests live in `src/test/java/`.

## Included patterns

### Creational
- Singleton (`singleton`)
- Factory Method (`factorymethod`)
- Builder (`builder`)

### Structural
- Adapter (`adapter`)
- Decorator (`decorator`)
- Composite (`composite`)

### Behavioral
- Strategy (`strategy`)
- Observer (`observer`)

## Build

```bash
./gradlew build
```

## Run one example

```bash
./gradlew runExample -Ppattern=strategy
```

Available values for `-Ppattern`: `singleton`, `factorymethod`, `builder`, `adapter`, `decorator`, `composite`, `strategy`, `observer`.

## Run all examples

```bash
./gradlew runAll
```

## Run tests

```bash
./gradlew test
```
