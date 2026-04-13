# Chain of Responsibility Pattern

## 1) Generic overview
Chain of Responsibility is a **behavioral pattern** that lets you pass requests along a chain of handlers. Each handler decides either to process the request or pass it to the next handler in the chain.

### Intent
- Decouple the sender of a request from its receivers.
- Allow multiple objects a chance to handle the request.
- Build processing pipelines dynamically.

### When to use
- Multiple objects can handle a request, and the handler isn't known in advance.
- You want to issue a request without specifying the receiver explicitly.
- The set of handlers or their order should be configurable at runtime.

### Pros
- Decouples sender from receiver.
- Flexible: handlers can be added, removed, or reordered.
- Each handler has a single responsibility.

### Cons
- No guarantee the request will be handled.
- Can be hard to debug if the chain is long.
- Performance may suffer with very long chains.

### Generic structure

```
+-----------+    +-----------+    +-----------+    +-----------+
| Handler A |--->| Handler B |--->| Handler C |--->|   null    |
+-----------+    +-----------+    +-----------+    +-----------+
| + handle()|    | + handle()|    | + handle()|
| - next    |    | - next    |    | - next    |
+-----------+    +-----------+    +-----------+
```

- `Handler`: declares the handling interface and holds a reference to the next handler.
- `ConcreteHandler`: handles requests it is responsible for; forwards others.
- `Client`: sends requests to the first handler in the chain.

### Related patterns
- **Command**: encapsulates a request as an object; chain of responsibility routes it.
- **Decorator**: both chain objects, but decorator adds behavior while chain tries to handle.
- **Composite**: a chain can be part of a composite tree structure.

### Real-world examples in Java
- `javax.servlet.Filter` — servlet filter chain.
- `java.util.logging.Logger` — logger hierarchy.
- Spring Security `FilterChain` — authentication/authorization pipeline.
- Exception handling: try/catch blocks form an implicit chain.

## 2) Concrete use case in this repository

**Use case:** a purchase approval flow where requests escalate through a hierarchy.

| Handler | Approval Limit | Escalates to |
|---------|---------------|-------------|
| `TeamLead` | $1,000 | Manager |
| `Manager` | $5,000 | Director |
| `Director` | $20,000 | CEO |
| `CEO` | $100,000 | (rejected if over) |

### Chain behavior
- If a handler can approve (amount <= limit), it approves immediately.
- If not, it forwards to the next handler.
- If no handler can approve, the request is rejected.

## 3) Example code walkthrough (`Example.java`)

### Demo 1: Full chain
5 requests of increasing amounts pass through the full chain (Team Lead -> Manager -> Director -> CEO).

### Demo 2: Shorter chain
A 2-handler chain (Manager -> Director) shows that chains are configurable, and requests exceeding all limits are rejected.

### Expected output

```
=== Chain: Team Lead -> Manager -> Director -> CEO ===

Processing: "Office supplies" ($500)
  [Team Lead] APPROVED "Office supplies" ($500) (limit: $1000)

Processing: "New laptop" ($2500)
  [Team Lead] Cannot approve "New laptop" ($2500) (limit: $1000) -> forwarding
  [Manager] APPROVED "New laptop" ($2500) (limit: $5000)

Processing: "Team offsite" ($15000)
  [Team Lead] Cannot approve "Team offsite" ($15000) (limit: $1000) -> forwarding
  [Manager] Cannot approve "Team offsite" ($15000) (limit: $5000) -> forwarding
  [Director] APPROVED "Team offsite" ($15000) (limit: $20000)

Processing: "Server cluster" ($75000)
  [Team Lead] Cannot approve "Server cluster" ($75000) (limit: $1000) -> forwarding
  [Manager] Cannot approve "Server cluster" ($75000) (limit: $5000) -> forwarding
  [Director] Cannot approve "Server cluster" ($75000) (limit: $20000) -> forwarding
  [CEO] APPROVED "Server cluster" ($75000) (limit: $100000)

Processing: "Building lease" ($500000)
  [Team Lead] Cannot approve "Building lease" ($500000) (limit: $1000) -> forwarding
  [Manager] Cannot approve "Building lease" ($500000) (limit: $5000) -> forwarding
  [Director] Cannot approve "Building lease" ($500000) (limit: $20000) -> forwarding
  [CEO] REJECTED "Building lease" ($500000) - exceeds all approval limits

=== Shorter chain: Manager -> Director ===

Processing: "Conference tickets" ($8000)
  [Manager] Cannot approve "Conference tickets" ($8000) (limit: $5000) -> forwarding
  [Director] APPROVED "Conference tickets" ($8000) (limit: $20000)

Processing: "Annual budget" ($50000)
  [Manager] Cannot approve "Annual budget" ($50000) (limit: $5000) -> forwarding
  [Director] REJECTED "Annual budget" ($50000) - exceeds all approval limits
```
