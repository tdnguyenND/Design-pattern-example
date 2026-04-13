# Proxy Pattern

## 1) Generic overview
Proxy is a **structural pattern** that provides a surrogate or placeholder for another object to control access to it.

### Intent
- Control access to an object (security, caching, logging, lazy loading).
- Add behavior before/after delegating to the real subject.
- The proxy and real subject share the same interface — the client doesn't know the difference.

### When to use
- Lazy initialization of expensive objects (virtual proxy).
- Access control (protection proxy).
- Caching results (caching proxy).
- Logging, metrics, or auditing (logging proxy).

### Pros
- Transparent to the client (same interface as the real object).
- Can add cross-cutting concerns without modifying the real object.
- Proxies can be composed (e.g., logging + caching).

### Cons
- Adds indirection and potential latency.
- Can make the system harder to debug.

### Generic structure

```
+-----------+       +-----------+
|  Client   |------>|  Subject  |
+-----------+       +-----------+
                    | + request()|
                    +-----------+
                         ^
                    +----+----+
                    |         |
              +---------+ +---------+
              |  Proxy  | |  Real   |
              +---------+ | Subject |
              | - real  | +---------+
              | + request()|
              +---------+
```

- `Subject`: common interface for proxy and real subject.
- `RealSubject`: the actual object that does the work.
- `Proxy`: holds a reference to the real subject, adds behavior, delegates.

### Related patterns
- **Decorator**: both wrap an object, but decorator adds behavior while proxy controls access.
- **Adapter**: changes the interface; proxy keeps the same interface.
- **Facade**: simplifies a complex subsystem; proxy controls access to a single object.

### Real-world examples in Java
- `java.lang.reflect.Proxy` — dynamic proxy for interface-based proxying.
- Spring AOP proxies — `@Transactional`, `@Cacheable`, `@Async`.
- `Collections.unmodifiableList()` — protection proxy.
- Hibernate lazy loading — virtual proxy for entities.

## 2) Concrete use case in this repository

**Use case:** an image loading system with multiple proxy types.

| Proxy | Purpose | Behavior |
|-------|---------|----------|
| `CachingImageProxy` | Avoids redundant disk reads | Stores results in a HashMap |
| `AccessControlProxy` | Restricts access by role | Checks role before delegating |
| `LoggingImageProxy` | Audits all calls | Logs call count, path, and result |

All proxies implement `ImageLoader` — the client uses the same `load(path)` method regardless.

## 3) Example code walkthrough (`Example.java`)

### Demo 1: Caching proxy
- First load hits disk, subsequent loads hit cache.
- Shows cache HIT/MISS behavior.

### Demo 2: Access control proxy
- Admin role is granted access.
- Guest role is denied.

### Demo 3: Logging proxy
- Logs every call with a counter.

### Demo 4: Composing proxies
- A logging proxy wraps a caching proxy wraps the real loader.
- Shows how proxies compose transparently.

### Expected output

```
=== Caching Proxy ===
  [Cache] MISS for: photo.jpg
  [Disk] Loading from disk: photo.jpg (slow)
  [Cache] MISS for: banner.png
  [Disk] Loading from disk: banner.png (slow)
  [Cache] HIT for: photo.jpg
  [Cache] HIT for: photo.jpg
Cache size: 2

=== Access Control Proxy ===
  [Access] GRANTED for role 'admin'
  [Disk] Loading from disk: secret.png (slow)

  [Access] DENIED for role 'guest' (requires 'admin')

=== Logging Proxy ===
  [Log] Call #1: load("icon.svg")
  [Disk] Loading from disk: icon.svg (slow)
  [Log] Result: OK
  [Log] Call #2: load("logo.png")
  [Disk] Loading from disk: logo.png (slow)
  [Log] Result: OK
Total calls: 2

=== Composing proxies: Logging + Caching ===
  [Log] Call #1: load("combo.jpg")
  [Cache] MISS for: combo.jpg
  [Disk] Loading from disk: combo.jpg (slow)
  [Log] Result: OK
  [Log] Call #2: load("combo.jpg")
  [Cache] HIT for: combo.jpg
  [Log] Result: OK
```
