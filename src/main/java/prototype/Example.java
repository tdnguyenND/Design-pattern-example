package prototype;

import java.util.ArrayList;
import java.util.List;

public class Example {

    // === Prototype interface ===
    interface DocumentPrototype {
        DocumentPrototype clone();
        void print();
    }

    // === Concrete prototype ===
    static class Document implements DocumentPrototype {
        private String title;
        private String content;
        private final List<String> tags;

        Document(String title, String content, List<String> tags) {
            this.title = title;
            this.content = content;
            this.tags = new ArrayList<>(tags);
        }

        // Copy constructor for cloning
        private Document(Document source) {
            this.title = source.title;
            this.content = source.content;
            this.tags = new ArrayList<>(source.tags);
        }

        public DocumentPrototype clone() {
            return new Document(this);
        }

        public void setTitle(String title) { this.title = title; }
        public void setContent(String content) { this.content = content; }
        public void addTag(String tag) { tags.add(tag); }

        public void print() {
            System.out.println("  Title:   " + title);
            System.out.println("  Content: " + content);
            System.out.println("  Tags:    " + tags);
            System.out.println();
        }
    }

    // === Template registry ===
    static class DocumentRegistry {
        private final java.util.Map<String, DocumentPrototype> templates = new java.util.HashMap<>();

        void register(String name, DocumentPrototype prototype) {
            templates.put(name, prototype);
        }

        DocumentPrototype create(String name) {
            DocumentPrototype prototype = templates.get(name);
            if (prototype == null) {
                throw new IllegalArgumentException("Unknown template: " + name);
            }
            return prototype.clone();
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Cloning a document ===");
        Document original = new Document(
                "Project Proposal",
                "We propose to build a new platform...",
                List.of("proposal", "draft")
        );

        System.out.println("Original:");
        original.print();

        Document copy = (Document) original.clone();
        copy.setTitle("Project Proposal (Copy)");
        copy.addTag("copy");

        System.out.println("Clone (modified):");
        copy.print();

        System.out.println("Original (unchanged):");
        original.print();

        System.out.println("=== Using a template registry ===");
        DocumentRegistry registry = new DocumentRegistry();
        registry.register("memo", new Document(
                "Memo", "To: Team\nFrom: Manager\n\n[body]",
                List.of("memo", "internal")
        ));
        registry.register("report", new Document(
                "Report", "Summary:\n\n[details]",
                List.of("report")
        ));

        Document memo1 = (Document) registry.create("memo");
        memo1.setTitle("Q1 Memo");
        memo1.setContent("To: Team\nFrom: Manager\n\nPlease review Q1 results.");

        Document memo2 = (Document) registry.create("memo");
        memo2.setTitle("Holiday Memo");

        Document report = (Document) registry.create("report");
        report.setTitle("Annual Report 2025");

        System.out.println("memo1:");
        memo1.print();

        System.out.println("memo2:");
        memo2.print();

        System.out.println("report:");
        report.print();
    }
}
