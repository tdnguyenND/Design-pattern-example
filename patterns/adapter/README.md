# Adapter Pattern

## 1) Generic overview
Adapter is a **structural pattern** that converts the interface of a class into another interface clients expect.

### Intent
- Reuse existing classes with incompatible interfaces.
- Integrate legacy or third-party APIs without changing client code.

### When to use
- You cannot modify an existing API, but must fit a required contract.
- You need a transition layer during migration.

### Pros
- Preserves existing code.
- Isolates incompatibility at one integration point.
- Simplifies client-side usage.

### Cons
- Adds an extra abstraction layer.
- If overused, may hide poor upstream API design.

### Generic structure
- `Target`: interface expected by client.
- `Adaptee`: existing/incompatible class.
- `Adapter`: implements `Target` and delegates to `Adaptee`.
- `Client`: depends only on `Target`.

## 2) Concrete use case in this repository
**Use case:** checkout flow requires `PaymentGateway`, but legacy SDK exposes `makeLegacyPayment(String)`.

## 3) Example code walkthrough (`Example.java`)
- `PaymentGateway` is the target interface.
- `LegacyPaymentSdk` is the adaptee.
- `LegacyPaymentAdapter` converts `long amountVnd` to legacy text format.
- `CheckoutService` consumes only `PaymentGateway`.
- `main` wires adapter + service and performs checkout.
