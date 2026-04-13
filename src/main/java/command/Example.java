package command;

import java.util.ArrayDeque;
import java.util.Deque;

public class Example {

    // === Command interface ===
    interface Command {
        void execute();
        void undo();
        String describe();
    }

    // === Receiver: the text buffer ===
    static class TextEditor {
        private final StringBuilder buffer = new StringBuilder();

        void insert(int position, String text) {
            buffer.insert(position, text);
        }

        void delete(int position, int length) {
            buffer.delete(position, position + length);
        }

        String getText() { return buffer.toString(); }
        int length() { return buffer.length(); }

        @Override
        public String toString() {
            return buffer.length() == 0 ? "(empty)" : "\"" + buffer + "\"";
        }
    }

    // === Concrete commands ===
    static class InsertCommand implements Command {
        private final TextEditor editor;
        private final int position;
        private final String text;

        InsertCommand(TextEditor editor, int position, String text) {
            this.editor = editor;
            this.position = position;
            this.text = text;
        }

        public void execute() { editor.insert(position, text); }
        public void undo() { editor.delete(position, text.length()); }
        public String describe() { return "Insert \"" + text + "\" at " + position; }
    }

    static class DeleteCommand implements Command {
        private final TextEditor editor;
        private final int position;
        private final int length;
        private String deletedText;

        DeleteCommand(TextEditor editor, int position, int length) {
            this.editor = editor;
            this.position = position;
            this.length = length;
        }

        public void execute() {
            deletedText = editor.getText().substring(position, position + length);
            editor.delete(position, length);
        }

        public void undo() { editor.insert(position, deletedText); }
        public String describe() { return "Delete " + length + " chars at " + position; }
    }

    // === Invoker: manages command history ===
    static class CommandHistory {
        private final Deque<Command> undoStack = new ArrayDeque<>();
        private final Deque<Command> redoStack = new ArrayDeque<>();

        void execute(Command cmd) {
            cmd.execute();
            undoStack.push(cmd);
            redoStack.clear();
            System.out.println("  Executed: " + cmd.describe());
        }

        boolean undo() {
            if (undoStack.isEmpty()) {
                System.out.println("  Nothing to undo");
                return false;
            }
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
            System.out.println("  Undo: " + cmd.describe());
            return true;
        }

        boolean redo() {
            if (redoStack.isEmpty()) {
                System.out.println("  Nothing to redo");
                return false;
            }
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
            System.out.println("  Redo: " + cmd.describe());
            return true;
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        CommandHistory history = new CommandHistory();

        System.out.println("=== Building text with commands ===");
        history.execute(new InsertCommand(editor, 0, "Hello"));
        System.out.println("  Buffer: " + editor);

        history.execute(new InsertCommand(editor, 5, " World"));
        System.out.println("  Buffer: " + editor);

        history.execute(new InsertCommand(editor, 5, ","));
        System.out.println("  Buffer: " + editor);

        history.execute(new DeleteCommand(editor, 0, 5));
        System.out.println("  Buffer: " + editor);

        System.out.println();
        System.out.println("=== Undo ===");
        history.undo();
        System.out.println("  Buffer: " + editor);

        history.undo();
        System.out.println("  Buffer: " + editor);

        history.undo();
        System.out.println("  Buffer: " + editor);

        System.out.println();
        System.out.println("=== Redo ===");
        history.redo();
        System.out.println("  Buffer: " + editor);

        history.redo();
        System.out.println("  Buffer: " + editor);

        System.out.println();
        System.out.println("=== New command clears redo stack ===");
        history.execute(new InsertCommand(editor, editor.length(), "!"));
        System.out.println("  Buffer: " + editor);
        history.redo();
    }
}
