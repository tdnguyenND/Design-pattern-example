package composite;

import java.util.ArrayList;
import java.util.List;

public class Example {

    // === Component interface: shared operations for both files and folders ===
    interface Node {
        void print(String indent);
        long size();
        int count();
    }

    // === Leaf: single file ===
    static class FileNode implements Node {
        private final String name;
        private final long sizeKb;

        FileNode(String name, long sizeKb) {
            this.name = name;
            this.sizeKb = sizeKb;
        }

        public void print(String indent) {
            System.out.printf("%s- %s (%d KB)%n", indent, name, sizeKb);
        }

        public long size() { return sizeKb; }
        public int count() { return 1; }
    }

    // === Composite: folder containing child Nodes ===
    static class FolderNode implements Node {
        private final String name;
        private final List<Node> children = new ArrayList<>();

        FolderNode(String name) {
            this.name = name;
        }

        public void add(Node node) {
            children.add(node);
        }

        public void remove(Node node) {
            children.remove(node);
        }

        public void print(String indent) {
            System.out.printf("%s+ %s/ (%d KB, %d files)%n",
                    indent, name, size(), count());
            for (Node child : children) {
                child.print(indent + "  ");
            }
        }

        public long size() {
            long total = 0;
            for (Node child : children) {
                total += child.size();
            }
            return total;
        }

        public int count() {
            int total = 0;
            for (Node child : children) {
                total += child.count();
            }
            return total;
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Building the directory tree ===");

        FolderNode root = new FolderNode("project");
        root.add(new FileNode("README.md", 3));
        root.add(new FileNode(".gitignore", 1));

        FolderNode src = new FolderNode("src");
        src.add(new FileNode("Main.java", 12));
        src.add(new FileNode("Utils.java", 8));
        src.add(new FileNode("Config.java", 5));

        FolderNode test = new FolderNode("test");
        test.add(new FileNode("MainTest.java", 10));
        test.add(new FileNode("UtilsTest.java", 7));

        FolderNode resources = new FolderNode("resources");
        resources.add(new FileNode("application.yml", 2));
        resources.add(new FileNode("logback.xml", 3));

        src.add(resources);
        root.add(src);
        root.add(test);

        root.print("");

        System.out.println();
        System.out.println("=== Uniform operations on Node ===");
        System.out.println("Total project size: " + root.size() + " KB");
        System.out.println("Total file count:   " + root.count());
        System.out.println();
        System.out.println("Only src folder:");
        System.out.println("  Size:       " + src.size() + " KB");
        System.out.println("  File count: " + src.count());
        System.out.println();
        System.out.println("Only test folder:");
        System.out.println("  Size:       " + test.size() + " KB");
        System.out.println("  File count: " + test.count());
    }
}
