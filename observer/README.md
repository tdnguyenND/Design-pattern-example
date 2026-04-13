# Observer Pattern

## 1) Generic overview
Observer is a **behavioral pattern** that defines a one-to-many dependency between objects. When the subject changes state, all observers are notified automatically.

### Intent
- Decouple event producers from event consumers.
- Enable publish/subscribe style updates.
- Support dynamic subscription and unsubscription.

### When to use
- Multiple components should react to one event source.
- You need extensible event handling without changing publisher code.
- You want runtime registration of listeners.
- Observers may maintain their own state across events.

### Pros
- Loose coupling between subject and subscribers.
- Easy to add new observers without modifying publisher.
- Natural fit for event-driven systems.

### Cons
- Notification order can be important and tricky.
- Too many observers can impact performance.
- Harder debugging in complex chains.
- Memory leak if you forget to unsubscribe.

### Generic structure

```
+---------------+         +-------------+
|    Subject    |-------->|  Observer    |
+---------------+         +-------------+
| - observers[] |         | + update()  |
| + subscribe() |         +-------------+
| + unsubscribe()|              ^
| + notify()    |               |
+---------------+      +-------+--------+
                       |                 |
               ObserverA           ObserverB
```

- `Subject` (Publisher): maintains subscriber list and notifies observers.
- `Observer`: update contract.
- `ConcreteObserver`: handles updates, may maintain its own state.

### Related patterns
- **Mediator**: centralizes communication between objects; observer distributes it (1-to-many).
- **Event Bus / Message Queue**: extends the observer pattern for distributed systems.
- **Strategy**: observer changes "who receives events"; strategy changes "which algorithm runs".

### Real-world examples in Java
- `java.util.EventListener` and all Swing listeners.
- `java.beans.PropertyChangeListener` / `PropertyChangeSupport`.
- Spring `ApplicationEventPublisher` / `@EventListener`.
- RxJava `Observable` / `Observer`.

## 2) Concrete use case in this repository

**Use case:** when a new order is created, multiple services need to react.

| Observer | Reaction | Stateful? |
|----------|----------|-----------|
| `WarehouseService` | Prepare shipment | No |
| `AccountingService` | Create invoice | No |
| `NotificationService` | Send confirmation email | No |
| `LoyaltyService` | Accumulate reward points | Yes (total points) |

Adding a new service (e.g. AnalyticsService) only requires implementing `OrderObserver` and calling `subscribe()` — no changes to the publisher.

## 3) Example code walkthrough (`Example.java`)

### Demo 1: Subscribe and receive events
- 4 observers register.
- 2 orders are created; all observers receive notifications.

### Demo 2: Unsubscribe
- `NotificationService` is unsubscribed.
- The next order only notifies the 3 remaining observers.

### Demo 3: Stateful observer
- `LoyaltyService` accumulates points across orders (1 point per 10,000 VND).
- After 3 orders, the total points are printed.

### Expected output

```
=== Registering observers ===
  [Subscribe] Warehouse
  [Subscribe] Accounting
  [Subscribe] Notification
  [Subscribe] Loyalty

>> New order: ORD-1001 (350,000 VND)
   [Warehouse] Preparing shipment for ORD-1001
   [Accounting] Created invoice 350,000 VND for ORD-1001
   [Notification] Sending confirmation email for ORD-1001
   [Loyalty] +35 points (total: 35 points)

>> New order: ORD-1002 (1,200,000 VND)
   [Warehouse] Preparing shipment for ORD-1002
   [Accounting] Created invoice 1,200,000 VND for ORD-1002
   [Notification] Sending confirmation email for ORD-1002
   [Loyalty] +120 points (total: 155 points)

=== Unsubscribing NotificationService ===
  [Unsubscribe] Notification
Remaining observers: 3

>> New order: ORD-1003 (500,000 VND)
   [Warehouse] Preparing shipment for ORD-1003
   [Accounting] Created invoice 500,000 VND for ORD-1003
   [Loyalty] +50 points (total: 205 points)

=== Loyalty retains state across events ===
Total accumulated points: 205 points
```
