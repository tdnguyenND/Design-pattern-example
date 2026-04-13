# State Pattern

## 1) Generic overview
State is a **behavioral pattern** that allows an object to alter its behavior when its internal state changes. The object will appear to change its class.

### Intent
- Encapsulate state-specific behavior in separate classes.
- Eliminate large conditional statements that depend on the object's state.
- Allow state transitions to happen cleanly.

### When to use
- An object's behavior depends on its state and changes at runtime.
- Operations have large conditional blocks based on the object's state.
- You have a clear state machine with defined transitions.

### Pros
- Organizes state-specific code into separate classes (SRP).
- Makes state transitions explicit.
- Eliminates complex conditional logic.
- Easy to add new states.

### Cons
- Can be overkill for simple state machines.
- Increases the number of classes.
- State transitions can be hard to follow across many state classes.

### Generic structure

```
+-----------+        +-------------+
|  Context  |------->|    State    |
+-----------+        +-------------+
| - state   |        | + handle()  |
| + request()|       +-------------+
+-----------+              ^
                     +-----+-----+
                     |           |
                StateA        StateB
```

- `State`: interface for state-specific behavior.
- `ConcreteState`: implements behavior for a particular state and triggers transitions.
- `Context`: maintains a reference to the current state and delegates behavior.

### Related patterns
- **Strategy**: same structure, but strategy is chosen by the client while state transitions internally.
- **State Machine**: state pattern is an OO implementation of a finite state machine.
- **Command**: commands are stateless actions; state encapsulates behavior that depends on context.

### Real-world examples in Java
- `java.util.Iterator` — `hasNext()`/`next()` behavior depends on position state.
- TCP connection states (LISTEN, ESTABLISHED, CLOSED).
- Spring State Machine (`spring-statemachine`).
- Android `Activity` lifecycle (CREATED, STARTED, RESUMED, etc.).

## 2) Concrete use case in this repository

**Use case:** an order status machine with the following transitions:

```
Pending -> Confirmed -> Shipped -> Delivered
   |           |
   v           v
Cancelled  Cancelled
```

| State | `next()` | `cancel()` |
|-------|----------|------------|
| `Pending` | -> Confirmed | -> Cancelled |
| `Confirmed` | -> Shipped | -> Cancelled |
| `Shipped` | -> Delivered | (not allowed) |
| `Delivered` | (terminal) | (not allowed) |
| `Cancelled` | (terminal) | (already cancelled) |

## 3) Example code walkthrough (`Example.java`)

### Demo 1: Happy path
Order goes through all states: Pending -> Confirmed -> Shipped -> Delivered.

### Demo 2: Cancel from Pending
Order is cancelled immediately. Further `next()` calls are rejected.

### Demo 3: Cancel from Confirmed
Order is confirmed then cancelled.

### Demo 4: Cannot cancel after Shipped
Once shipped, cancellation is not allowed.

### Expected output

```
=== Happy path: Pending -> Confirmed -> Shipped -> Delivered ===
  [ORD-001] Action: next (current: Pending)
  [ORD-001] Pending -> Confirmed
  [ORD-001] Action: next (current: Confirmed)
  [ORD-001] Confirmed -> Shipped
  [ORD-001] Action: next (current: Shipped)
  [ORD-001] Shipped -> Delivered
  [ORD-001] Action: next (current: Delivered)
  [ORD-001] Already delivered: no next state

=== Cancel from Pending ===
  [ORD-002] Action: cancel (current: Pending)
  [ORD-002] Pending -> Cancelled
  [ORD-002] Action: next (current: Cancelled)
  [ORD-002] Cannot proceed: order is cancelled

=== Cancel from Confirmed ===
  [ORD-003] Action: next (current: Pending)
  [ORD-003] Pending -> Confirmed
  [ORD-003] Action: cancel (current: Confirmed)
  [ORD-003] Confirmed -> Cancelled

=== Cannot cancel after Shipped ===
  [ORD-004] Action: next (current: Pending)
  [ORD-004] Pending -> Confirmed
  [ORD-004] Action: next (current: Confirmed)
  [ORD-004] Confirmed -> Shipped
  [ORD-004] Action: cancel (current: Shipped)
  [ORD-004] Cannot cancel: already shipped
```
