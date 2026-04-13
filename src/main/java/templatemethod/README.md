# Template Method Pattern

## 1) Generic overview
Template Method is a **behavioral pattern** that defines the skeleton of an algorithm in a base class, letting subclasses override specific steps without changing the overall structure.

### Intent
- Define the algorithm structure once in a base class.
- Let subclasses customize specific steps.
- Avoid code duplication across similar algorithms.

### When to use
- Multiple classes share the same algorithm structure but differ in details.
- You want to enforce a specific sequence of steps.
- You want subclasses to extend specific steps without altering the workflow.

### Pros
- Eliminates code duplication.
- Controls the algorithm flow from the base class.
- Subclasses only override what they need (hooks).

### Cons
- Relies on inheritance (less flexible than composition).
- Can be hard to maintain if the template has many steps.
- Subclasses are constrained by the base class structure.

### Generic structure

```
+-----------------------------+
|     AbstractClass           |
+-----------------------------+
| + templateMethod() {final}  |
|   step1()                   |
|   step2()                   |
|   hook()                    |
+-----------------------------+
| # step1() {abstract}        |
| # step2() {abstract}        |
| # hook() {default impl}     |
+-----------------------------+
            ^
     +------+------+
     |             |
ConcreteClassA  ConcreteClassB
```

- `AbstractClass`: defines `templateMethod()` (often `final`) and abstract/hook steps.
- `ConcreteClass`: overrides abstract steps and optionally hooks.
- `templateMethod()`: calls steps in a fixed order.

### Related patterns
- **Strategy**: uses composition to swap entire algorithms; template method uses inheritance to swap steps.
- **Factory Method**: often used as a step inside a template method.
- **Hook methods**: optional steps with default implementations.

### Real-world examples in Java
- `java.util.AbstractList.get()` — subclasses implement `get(int)`, base class provides `iterator()`, `indexOf()`, etc.
- `java.io.InputStream.read()` — subclasses implement single-byte `read()`, base provides `read(byte[])`.
- Spring `AbstractController.handleRequest()` — template for HTTP request handling.
- JUnit lifecycle: `@BeforeEach` → `@Test` → `@AfterEach`.

## 2) Concrete use case in this repository

**Use case:** exporting tabular data to multiple formats (CSV, JSON, Markdown).

| Exporter | `formatHeader` | `formatRow` | `rowSeparator` | `formatFooter` |
|----------|---------------|-------------|----------------|----------------|
| `CsvExporter` | Column names joined by `,` | Values joined by `,` | `\n` (default) | (none) |
| `JsonExporter` | `[` | `{"key": "value", ...}` | `,\n` | `]` |
| `MarkdownExporter` | Header + separator row | `\| val1 \| val2 \|` | `\n` (default) | (none) |

The template method `export()` is `final` and defines: header → rows → footer. Subclasses only implement the format-specific parts.

## 3) Example code walkthrough (`Example.java`)

### Template method: `export()`
1. Format header from column names.
2. Format each data row.
3. Join rows with separator.
4. Append footer.

### Hook methods
- `rowSeparator()`: default is `\n`, JSON overrides to `,\n`.
- `formatFooter()`: default is empty, JSON overrides to `\n]`.

### Expected output

```
=== CSV Export ===
name,role,city
Alice,Engineer,Hanoi
Bob,Designer,HCMC
Carol,Manager,Danang

=== JSON Export ===
[
  {"name": "Alice", "role": "Engineer", "city": "Hanoi"},
  {"name": "Bob", "role": "Designer", "city": "HCMC"},
  {"name": "Carol", "role": "Manager", "city": "Danang"}
]

=== Markdown Export ===
| name | role | city |
| ---- | ---- | ---- |
| Alice | Engineer | Hanoi |
| Bob | Designer | HCMC |
| Carol | Manager | Danang |
```
