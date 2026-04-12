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

### Pros
- Loose coupling between subject and subscribers.
- Easy to add new observers.
- Natural fit for event-driven systems.

### Cons
- Notification order can be important and tricky.
- Too many observers can impact performance.
- Harder debugging in complex chains.

### Generic structure
- `Subject`: maintains subscriber list and notifies observers.
- `Observer`: update contract.
- `ConcreteObserver`: handles updates.

## 2) Concrete use case in this repository
**Use case:** when a new order is created, multiple dashboards must update.

- Warehouse dashboard prepares fulfillment.
- Accounting dashboard prepares invoicing.

## 3) Example code walkthrough (`Example.java`)
- `OrderObserver` defines `onOrderCreated(String orderId)`.
- `OrderPublisher` stores observers and supports `subscribe`, `unsubscribe`, and `publishOrder`.
- `WarehouseDashboard` and `AccountingDashboard` implement the observer interface.
- `main` subscribes both dashboards and publishes two orders to show fan-out notifications.
