# Facade Pattern

## 1) Generic overview
Facade is a **structural pattern** that provides a simplified interface to a complex subsystem, hiding its internal complexity from clients.

### Intent
- Provide a simple entry point to a complex subsystem.
- Reduce coupling between clients and subsystem internals.
- Coordinate multiple subsystem operations into a single workflow.

### When to use
- A subsystem has many interdependent classes and the client only needs a subset of its functionality.
- You want to layer your system and provide clean entry points to each layer.
- You need to orchestrate multiple services into a single operation.

### Pros
- Simplifies client code.
- Decouples clients from subsystem implementation details.
- Acts as a single coordination point for complex workflows.

### Cons
- Can become a "god object" if it takes on too many responsibilities.
- Doesn't prevent clients from using subsystem classes directly.

### Generic structure

```
+----------+       +------------------+       +-----------+
|  Client  |------>|     Facade       |------>| SubsystemA|
+----------+       +------------------+       +-----------+
                   | + simpleMethod() |------>| SubsystemB|
                   +------------------+       +-----------+
                                       ------>| SubsystemC|
                                              +-----------+
```

- `Facade`: provides simplified methods that delegate to subsystem classes.
- `Subsystem classes`: perform the actual work, unaware of the facade.
- `Client`: interacts only with the facade.

### Related patterns
- **Adapter**: converts one interface to another; facade simplifies a complex interface.
- **Mediator**: coordinates communication between peers; facade provides a one-way simplified interface.
- **Singleton**: facades are often singletons.

### Real-world examples in Java
- `javax.faces.context.FacesContext` — facade for JSF subsystems.
- Spring `JdbcTemplate` — simplifies JDBC operations.
- SLF4J `LoggerFactory` — facade over various logging frameworks.

## 2) Concrete use case in this repository

**Use case:** an e-commerce order placement that coordinates inventory, payment, shipping, and notification services.

| Subsystem | Responsibility |
|-----------|---------------|
| `InventoryService` | Check stock, reserve items |
| `PaymentService` | Charge customer, handle refunds |
| `ShippingService` | Ship items, generate tracking |
| `NotificationService` | Send confirmation or failure emails |

`OrderFacade.placeOrder()` orchestrates all four services in the correct order and handles failures gracefully.

## 3) Example code walkthrough (`Example.java`)

### Demo 1: Successful order
All checks pass — inventory reserved, payment charged, item shipped, confirmation sent.

### Demo 2: Out of stock
Inventory check fails — customer is notified, no payment is charged.

### Demo 3: Payment declined
Inventory is available but payment fails — customer is notified, no items are reserved.

### Expected output

```
=== Successful order ===
Processing order for customer-1...
  [Inventory] Checking stock for LAPTOP-01 x2
  [Inventory] In stock
  [Payment] Charging customer-1: $1999.99
  [Payment] Approved
  [Inventory] Reserved LAPTOP-01 x2
  [Shipping] Shipping LAPTOP-01 to 123 Main St
  [Shipping] Tracking: TRK-XXXXX
  [Notification] Sent confirmation to customer-1 (tracking: TRK-XXXXX)
Order completed successfully!

=== Failed order: out of stock ===
Processing order for customer-2...
  [Inventory] Checking stock for PHONE-01 x999
  [Inventory] Out of stock
  [Notification] Sent failure notice to customer-2: Out of stock

=== Failed order: payment declined ===
Processing order for customer-3...
  [Inventory] Checking stock for SERVER-01 x1
  [Inventory] In stock
  [Payment] Charging customer-3: $99999.99
  [Payment] Declined
  [Notification] Sent failure notice to customer-3: Payment declined
```

> Note: `TRK-XXXXX` will vary at runtime since it's based on `System.nanoTime()`.
