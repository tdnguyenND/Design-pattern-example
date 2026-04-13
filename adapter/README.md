# Adapter Pattern

## 1) Generic overview
Adapter is a **structural pattern** that converts the interface of a class into another interface clients expect.

### Intent
- Reuse existing classes with incompatible interfaces.
- Integrate legacy or third-party APIs without changing client code.

### When to use
- You cannot modify an existing API, but must fit a required contract.
- You need a transition layer during migration.
- Integrating multiple external systems through a unified interface.

### Pros
- Preserves existing code.
- Isolates incompatibility at one integration point.
- Simplifies client-side usage.

### Cons
- Adds an extra abstraction layer.
- If overused, may hide poor upstream API design.

### Generic structure

```
+------------+     +-----------+     +------------+
|   Client   |---->|  Target   |     |  Adaptee   |
+------------+     +-----------+     +------------+
                   | + request()|     | + specific |
                   +-----------+     |   Request()|
                        ^            +------------+
                        |                  ^
                   +-----------+           |
                   |  Adapter  |-----------+
                   +-----------+
                   | + request()| delegates to
                   +-----------+ specificRequest()
```

- `Target`: interface expected by client.
- `Adaptee`: existing/incompatible class.
- `Adapter`: implements `Target` and delegates to `Adaptee`.
- `Client`: depends only on `Target`.

### Related patterns
- **Facade**: also wraps another system, but facade simplifies the interface while adapter converts it.
- **Decorator**: also wraps objects, but decorator adds behavior while adapter converts the interface.
- **Bridge**: separates abstraction from implementation upfront; adapter fixes incompatibility after the fact.

### Real-world examples in Java
- `Arrays.asList(T[])` — adapter from array to List.
- `InputStreamReader` — adapter from byte stream to character stream.
- `Collections.enumeration(Collection)` — adapter to Enumeration.

## 2) Concrete use case in this repository

**Use case:** a checkout system requires `PaymentGateway`, but the legacy SDK has an incompatible interface.

| Class | Role | Interface |
|-------|------|-----------|
| `PaymentGateway` | Target | `pay(long)`, `getName()` |
| `LegacyPaymentSdk` | Adaptee | `makeLegacyPayment(String)` |
| `LegacyPaymentAdapter` | Adapter | Converts `long` to `String` format |
| `MomoGateway` | Modern impl | Directly compatible |
| `VnPayGateway` | Modern impl | Directly compatible |
| `CheckoutService` | Client | Only knows `PaymentGateway` |

The adapter allows the legacy SDK to work alongside modern gateways — `CheckoutService` doesn't need to know the difference.

## 3) Example code walkthrough (`Example.java`)

### Flow
1. `LegacyPaymentSdk` only accepts `String`, but the new system uses `long amountVnd`.
2. `LegacyPaymentAdapter` wraps the old SDK, converting `long` to formatted `String` (`"199,000 VND"`).
3. `CheckoutService` uses the `PaymentGateway` interface — unaware of whether it's using legacy or modern.
4. The demo shows all 3 gateways (legacy + 2 modern) working through the same interface.

### Expected output

```
=== Adapter: wrapping the Legacy SDK ===
Order ORD-001 - using Legacy Bank:
  [Legacy SDK] Payment: 199,000 VND
  Done!

=== Modern gateways: directly compatible ===
Order ORD-002 - using MoMo:
  [MoMo] Payment: 350,000 VND
  Done!

Order ORD-003 - using VNPay:
  [VNPay] Payment: 1,250,000 VND
  Done!

=== All share the same PaymentGateway interface ===
- Legacy Bank
- MoMo
- VNPay
```
