# Composite Pattern

## 1) Generic overview
Composite is a **structural pattern** that composes objects into tree structures so clients can treat individual objects and compositions uniformly.

### Intent
- Represent part-whole hierarchies.
- Provide a single interface for leaf and composite nodes.
- Allow the same operation to be called on both individual objects and groups.

### When to use
- You model recursive structures (file system, UI tree, org chart).
- Clients should execute operations uniformly across node types.
- You need recursive computations (total size, count, search).

### Pros
- Simplifies client code.
- Scales naturally with recursive data.
- Easy to add new node types.

### Cons
- Designing a common interface can be tricky.
- May reduce strict type safety for specific operations.

### Generic structure

```
+-------------+
|  Component  |
+-------------+
| + operation()|
| + size()     |
+-------------+
      ^
      |
+-----+------+--------+
|                      |
+--------+      +-------------+
|  Leaf  |      |  Composite  |
+--------+      +-------------+
                | - children   |
                | + add()      |
                | + remove()   |
                | + operation()|  // delegates to children
                +-------------+
```

- `Component`: shared operation contract.
- `Leaf`: terminal node, implements operations directly.
- `Composite`: contains children and delegates recursively.

### Related patterns
- **Decorator**: both use recursive composition, but decorator adds behavior while composite organizes a tree.
- **Iterator**: used to traverse a composite tree.
- **Visitor**: adds new operations to a composite tree without modifying node classes.

### Real-world examples in Java
- `java.awt.Component` / `java.awt.Container`
- XML DOM (`Node`, `Element`, `Document`)
- Spring `CompositeHealthIndicator`
- File system: `java.io.File` (pre Java 7) / `java.nio.file.Path`

## 2) Concrete use case in this repository

**Use case:** a project directory tree with files and nested folders.

| Class | Role | Operations |
|-------|------|------------|
| `Node` | Component | `print()`, `size()`, `count()` |
| `FileNode` | Leaf | Returns its own size/count directly |
| `FolderNode` | Composite | Aggregates recursively from children |

### Uniform operations
- `size()`: FileNode returns the file size; FolderNode sums all children.
- `count()`: FileNode returns 1; FolderNode counts all files in its subtree.
- `print()`: Prints the tree recursively with indentation; folders show total size and file count.

## 3) Example code walkthrough (`Example.java`)

### Directory tree

```
project/
├── README.md (3 KB)
├── .gitignore (1 KB)
├── src/
│   ├── Main.java (12 KB)
│   ├── Utils.java (8 KB)
│   ├── Config.java (5 KB)
│   └── resources/
│       ├── application.yml (2 KB)
│       └── logback.xml (3 KB)
└── test/
    ├── MainTest.java (10 KB)
    └── UtilsTest.java (7 KB)
```

### Demo
1. Build a nested directory tree.
2. Print the entire tree with size and file count at each folder.
3. Call `size()` and `count()` on root, src, test — same interface, automatic recursive results.

### Expected output

```
=== Building the directory tree ===
+ project/ (51 KB, 9 files)
  - README.md (3 KB)
  - .gitignore (1 KB)
  + src/ (30 KB, 5 files)
    - Main.java (12 KB)
    - Utils.java (8 KB)
    - Config.java (5 KB)
    + resources/ (5 KB, 2 files)
      - application.yml (2 KB)
      - logback.xml (3 KB)
  + test/ (17 KB, 2 files)
    - MainTest.java (10 KB)
    - UtilsTest.java (7 KB)

=== Uniform operations on Node ===
Total project size: 51 KB
Total file count:   9

Only src folder:
  Size:       30 KB
  File count: 5

Only test folder:
  Size:       17 KB
  File count: 2
```
