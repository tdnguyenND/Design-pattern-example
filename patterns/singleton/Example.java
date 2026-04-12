public class Example {

    static class AppConfig {
        private static final AppConfig INSTANCE = new AppConfig();

        private final String appName;
        private final String environment;

        private AppConfig() {
            this.appName = "PatternHub";
            this.environment = "dev";
        }

        public static AppConfig getInstance() {
            return INSTANCE;
        }

        public String getAppName() {
            return appName;
        }

        public String getEnvironment() {
            return environment;
        }
    }

    public static void main(String[] args) {
        AppConfig c1 = AppConfig.getInstance();
        AppConfig c2 = AppConfig.getInstance();

        System.out.println(c1.getAppName() + " - " + c1.getEnvironment());
        System.out.println("Same instance? " + (c1 == c2));
    }
}
