# Command Pattern

## 1) Generic overview
Command is a **behavioral pattern** that encapsulates a request as an object, allowing you to parameterize clients with different requests, queue or log requests, and support undoable operations.

### Intent
- Decouple the object that invokes an operation from the object that performs it.
- Support undo/redo by storing command history.
- Enable queuing, logging, and macro recording of operations.

### When to use
- You need undo/redo functionality.
- You want to queue, schedule, or log operations.
- You need to parameterize objects with actions (callbacks as objects).

### Pros
- Decouples invoker from receiver.
- Commands can be composed into macros.
- Supports undo/redo naturally.
- Easy to add new commands without changing existing code.

### Cons
- Can result in many small command classes.
- Undo logic can be complex for certain operations.

### Generic structure

```
+-----------+       +-----------+       +-----------+
|  Invoker  |------>|  Command  |------>| Receiver  |
+-----------+       +-----------+       +-----------+
| - history |       | + execute()|      | + action()|
| + execute()|      | + undo()  |       +-----------+
| + undo()  |       +-----------+
| + redo()  |            ^
+-----------+       +----+----+
                    |         |
               InsertCmd  DeleteCmd
```

- `Command`: interface with `execute()` and `undo()`.
- `ConcreteCommand`: captures receiver + parameters needed for the operation.
- `Receiver`: the object that actually performs the work.
- `Invoker`: stores and manages command history.

### Related patterns
- **Memento**: can store state snapshots for complex undo; command stores inverse operations.
- **Strategy**: both encapsulate algorithms, but command adds undo and history.
- **Chain of Responsibility**: commands can be chained for sequential processing.

### Real-world examples in Java
- `java.lang.Runnable` — a command with no undo.
- `javax.swing.Action` — command for UI actions.
- Spring `CommandLineRunner` — encapsulates startup commands.

## 2) Concrete use case in this repository

**Use case:** a text editor with insert/delete operations and undo/redo support.

| Component | Role |
|-----------|------|
| `TextEditor` | Receiver — manages the text buffer |
| `InsertCommand` | Inserts text at a position; undo removes it |
| `DeleteCommand` | Deletes text at a position; undo re-inserts it |
| `CommandHistory` | Invoker — manages undo/redo stacks |

## 3) Example code walkthrough (`Example.java`)

### Demo 1: Building text
- Insert "Hello", " World", "," — then delete "Hello".
- Each command is recorded in the undo stack.

### Demo 2: Undo
- Undo the delete, undo the comma, undo " World".
- Buffer reverts step by step.

### Demo 3: Redo
- Redo two undone commands.

### Demo 4: New command clears redo
- After a new command, redo stack is cleared — standard editor behavior.

### Expected output

```
=== Building text with commands ===
  Executed: Insert "Hello" at 0
  Buffer: "Hello"
  Executed: Insert " World" at 5
  Buffer: "Hello World"
  Executed: Insert "," at 5
  Buffer: "Hello, World"
  Executed: Delete 5 chars at 0
  Buffer: ", World"

=== Undo ===
  Undo: Delete 5 chars at 0
  Buffer: "Hello, World"
  Undo: Insert "," at 5
  Buffer: "Hello World"
  Undo: Insert " World" at 5
  Buffer: "Hello"

=== Redo ===
  Redo: Insert " World" at 5
  Buffer: "Hello World"
  Redo: Insert "," at 5
  Buffer: "Hello, World"

=== New command clears redo stack ===
  Executed: Insert "!" at 12
  Buffer: "Hello, World!"
  Nothing to redo
```
