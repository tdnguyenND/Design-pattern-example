public class Example {

    static class Report {
        private final String title;
        private final String author;
        private final boolean includeChart;
        private final boolean confidential;

        private Report(Builder builder) {
            this.title = builder.title;
            this.author = builder.author;
            this.includeChart = builder.includeChart;
            this.confidential = builder.confidential;
        }

        static class Builder {
            private String title;
            private String author;
            private boolean includeChart;
            private boolean confidential;

            public Builder title(String title) {
                this.title = title;
                return this;
            }

            public Builder author(String author) {
                this.author = author;
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

            public Report build() {
                return new Report(this);
            }
        }

        @Override
        public String toString() {
            return "Report{" +
                    "title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", includeChart=" + includeChart +
                    ", confidential=" + confidential +
                    '}';
        }
    }

    public static void main(String[] args) {
        Report monthlyReport = new Report.Builder()
                .title("Monthly Revenue")
                .author("Finance Team")
                .includeChart(true)
                .confidential(true)
                .build();

        System.out.println(monthlyReport);
    }
}
