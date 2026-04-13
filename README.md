# Design Pattern Examples (Java)

This repository is a curated set of Java examples for common software design patterns.

## Prerequisites

- Java 17+

## Structure

```
src/main/java/
├── singleton/          # Creational
├── factorymethod/
├── abstractfactory/
├── prototype/
├── builder/
├── adapter/            # Structural
├── decorator/
├── composite/
├── facade/
├── proxy/
├── strategy/           # Behavioral
├── observer/
├── command/
├── templatemethod/
├── state/
└── chain/              # Chain of Responsibility
```

Each pattern package contains:
- `Example.java`: a runnable example with a `main` method.
- `README.md`: explains the problem, how the pattern works, key roles, and example flow.

Smoke tests live in `src/test/java/`.

## Included patterns

### Creational
- Singleton (`singleton`)
- Factory Method (`factorymethod`)
- Abstract Factory (`abstractfactory`)
- Prototype (`prototype`)
- Builder (`builder`)

### Structural
- Adapter (`adapter`)
- Decorator (`decorator`)
- Composite (`composite`)
- Facade (`facade`)
- Proxy (`proxy`)

### Behavioral
- Strategy (`strategy`)
- Observer (`observer`)
- Command (`command`)
- Template Method (`templatemethod`)
- State (`state`)
- Chain of Responsibility (`chain`)

## Build

```bash
./gradlew build
```

## Run one example

```bash
./gradlew runExample -Ppattern=strategy
```

Available values for `-Ppattern`: `singleton`, `factorymethod`, `abstractfactory`, `prototype`, `builder`, `adapter`, `decorator`, `composite`, `facade`, `proxy`, `strategy`, `observer`, `command`, `templatemethod`, `state`, `chain`.

## Run all examples

```bash
./gradlew runAll
```

## Run tests

```bash
./gradlew test
```
