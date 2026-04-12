import java.util.ArrayList;
import java.util.List;

public class Example {

    interface Node {
        void print(String indent);
    }

    static class FileNode implements Node {
        private final String name;

        FileNode(String name) {
            this.name = name;
        }

        public void print(String indent) {
            System.out.println(indent + "- " + name);
        }
    }

    static class FolderNode implements Node {
        private final String name;
        private final List<Node> children = new ArrayList<>();

        FolderNode(String name) {
            this.name = name;
        }

        public void add(Node node) {
            children.add(node);
        }

        public void print(String indent) {
            System.out.println(indent + "+ " + name);
            for (Node child : children) {
                child.print(indent + "  ");
            }
        }
    }

    public static void main(String[] args) {
        FolderNode root = new FolderNode("root");
        root.add(new FileNode("README.md"));

        FolderNode src = new FolderNode("src");
        src.add(new FileNode("Main.java"));
        src.add(new FileNode("Utils.java"));

        FolderNode resources = new FolderNode("resources");
        resources.add(new FileNode("application.yml"));

        root.add(src);
        root.add(resources);

        root.print("");
    }
}
