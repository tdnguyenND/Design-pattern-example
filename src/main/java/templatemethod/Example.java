package templatemethod;

import java.util.List;

public class Example {

    // === Abstract class with template method ===
    static abstract class DataExporter {

        // Template method: defines the algorithm skeleton
        final String export(List<String[]> data) {
            String header = formatHeader(data.get(0));
            StringBuilder body = new StringBuilder();
            for (int i = 1; i < data.size(); i++) {
                body.append(formatRow(data.get(i)));
                if (i < data.size() - 1) body.append(rowSeparator());
            }
            String footer = formatFooter(data.size() - 1);
            return header + body + footer;
        }

        // Steps to be implemented by subclasses
        abstract String formatHeader(String[] columns);
        abstract String formatRow(String[] values);

        // Hooks with default implementations
        String rowSeparator() { return "\n"; }
        String formatFooter(int rowCount) { return ""; }
    }

    // === Concrete: CSV export ===
    static class CsvExporter extends DataExporter {
        String formatHeader(String[] columns) {
            return String.join(",", columns) + "\n";
        }

        String formatRow(String[] values) {
            return String.join(",", values);
        }
    }

    // === Concrete: JSON export ===
    static class JsonExporter extends DataExporter {
        private String[] columns;

        String formatHeader(String[] columns) {
            this.columns = columns;
            return "[\n";
        }

        String formatRow(String[] values) {
            StringBuilder sb = new StringBuilder("  {");
            for (int i = 0; i < columns.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append("\"").append(columns[i]).append("\": \"").append(values[i]).append("\"");
            }
            sb.append("}");
            return sb.toString();
        }

        String rowSeparator() { return ",\n"; }
        String formatFooter(int rowCount) { return "\n]"; }
    }

    // === Concrete: Markdown table export ===
    static class MarkdownExporter extends DataExporter {
        String formatHeader(String[] columns) {
            String header = "| " + String.join(" | ", columns) + " |\n";
            String separator = "|";
            for (String col : columns) {
                separator += " " + "-".repeat(col.length()) + " |";
            }
            return header + separator + "\n";
        }

        String formatRow(String[] values) {
            return "| " + String.join(" | ", values) + " |";
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        List<String[]> data = List.of(
                new String[]{"name", "role", "city"},
                new String[]{"Alice", "Engineer", "Hanoi"},
                new String[]{"Bob", "Designer", "HCMC"},
                new String[]{"Carol", "Manager", "Danang"}
        );

        System.out.println("=== CSV Export ===");
        System.out.println(new CsvExporter().export(data));

        System.out.println();
        System.out.println("=== JSON Export ===");
        System.out.println(new JsonExporter().export(data));

        System.out.println();
        System.out.println("=== Markdown Export ===");
        System.out.println(new MarkdownExporter().export(data));
    }
}
