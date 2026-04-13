public class Example {

    static class Report {
        private final String title;
        private final String author;
        private final String content;
        private final boolean includeChart;
        private final boolean confidential;
        private final String format;

        private Report(Builder builder) {
            this.title = builder.title;
            this.author = builder.author;
            this.content = builder.content;
            this.includeChart = builder.includeChart;
            this.confidential = builder.confidential;
            this.format = builder.format;
        }

        static class Builder {
            // Required
            private final String title;

            // Optional (with defaults)
            private String author = "Unknown";
            private String content = "";
            private boolean includeChart = false;
            private boolean confidential = false;
            private String format = "PDF";

            public Builder(String title) {
                this.title = title;
            }

            public Builder author(String author) {
                this.author = author;
                return this;
            }

            public Builder content(String content) {
                this.content = content;
                return this;
            }

            public Builder includeChart(boolean includeChart) {
                this.includeChart = includeChart;
                return this;
            }

            public Builder confidential(boolean confidential) {
                this.confidential = confidential;
                return this;
            }

            public Builder format(String format) {
                this.format = format;
                return this;
            }

            public Report build() {
                if (title == null || title.isBlank()) {
                    throw new IllegalStateException("Title must not be empty");
                }
                if (confidential && format.equals("HTML")) {
                    throw new IllegalStateException("Confidential reports cannot be exported as HTML");
                }
                return new Report(this);
            }
        }

        public void print() {
            System.out.println("--- Report ---");
            System.out.println("Title:        " + title);
            System.out.println("Author:       " + author);
            System.out.println("Content:      " + (content.isEmpty() ? "(empty)" : content));
            System.out.println("Chart:        " + (includeChart ? "Yes" : "No"));
            System.out.println("Confidential: " + (confidential ? "Yes" : "No"));
            System.out.println("Format:       " + format);
            System.out.println();
        }
    }

    // === Director: create reports from pre-configured templates ===
    static class ReportDirector {
        public Report.Builder monthlyFinance() {
            return new Report.Builder("Monthly Financial Report")
                    .author("Accounting Team")
                    .includeChart(true)
                    .confidential(true)
                    .format("PDF");
        }

        public Report.Builder publicNewsletter() {
            return new Report.Builder("Internal Newsletter")
                    .author("Communications Team")
                    .includeChart(false)
                    .confidential(false)
                    .format("HTML");
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Building manually with Builder ===");

        Report simpleReport = new Report.Builder("Quick Status")
                .build();
        simpleReport.print();

        Report detailedReport = new Report.Builder("Q1 Revenue Analysis")
                .author("Data Team")
                .content("Revenue increased 15% compared to Q4 last year")
                .includeChart(true)
                .confidential(true)
                .format("PDF")
                .build();
        detailedReport.print();

        System.out.println("=== Using Director (pre-configured templates) ===");

        ReportDirector director = new ReportDirector();

        Report financeReport = director.monthlyFinance()
                .content("Total revenue: $2.5M")
                .build();
        financeReport.print();

        Report newsletter = director.publicNewsletter()
                .content("Congrats to the dev team for completing sprint 42!")
                .build();
        newsletter.print();

        System.out.println("=== Validation in build() ===");
        try {
            new Report.Builder("Secret")
                    .confidential(true)
                    .format("HTML")
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
