# Composite Pattern

## 1) Generic overview
Composite is a **structural pattern** that composes objects into tree structures so clients can treat individual objects and compositions uniformly.

### Intent
- Represent part-whole hierarchies.
- Provide a single interface for leaf and composite nodes.

### When to use
- You model recursive structures (file system, UI tree, org chart).
- Clients should execute operations uniformly across node types.

### Pros
- Simplifies client code.
- Scales naturally with recursive data.
- Easy to add new node types.

### Cons
- Designing a common interface can be tricky.
- May reduce strict type safety for specific operations.

### Generic structure
- `Component`: shared operation contract.
- `Leaf`: terminal node.
- `Composite`: contains children and delegates recursively.

## 2) Concrete use case in this repository
**Use case:** a file tree where folders contain files and nested folders.

## 3) Example code walkthrough (`Example.java`)
- `Node` is the common component interface (`print`).
- `FileNode` is the leaf.
- `FolderNode` is the composite with `List<Node>` children.
- `main` builds a nested tree and prints it recursively from root.
