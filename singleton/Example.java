public class Example {

    // === Approach 1: Eager initialization (thread-safe, simple) ===
    static class AppConfig {
        private static final AppConfig INSTANCE = new AppConfig();

        private final String appName;
        private final String environment;
        private int requestCount;

        private AppConfig() {
            this.appName = "PatternHub";
            this.environment = "production";
            this.requestCount = 0;
        }

        public static AppConfig getInstance() {
            return INSTANCE;
        }

        public String getAppName() { return appName; }
        public String getEnvironment() { return environment; }

        public void incrementRequestCount() { requestCount++; }
        public int getRequestCount() { return requestCount; }
    }

    // === Approach 2: Lazy initialization with double-checked locking ===
    static class DatabaseConnection {
        private static volatile DatabaseConnection instance;

        private final String url;
        private boolean connected;

        private DatabaseConnection(String url) {
            this.url = url;
            this.connected = false;
            System.out.println("[DB] Initializing connection to: " + url);
        }

        public static DatabaseConnection getInstance() {
            if (instance == null) {
                synchronized (DatabaseConnection.class) {
                    if (instance == null) {
                        instance = new DatabaseConnection("jdbc:mysql://localhost:3306/mydb");
                    }
                }
            }
            return instance;
        }

        public void connect() {
            connected = true;
            System.out.println("[DB] Connected successfully: " + url);
        }

        public boolean isConnected() { return connected; }
    }

    // === Approach 3: Enum singleton (best practice per Effective Java) ===
    enum Logger {
        INSTANCE;

        public void info(String message) {
            System.out.println("[INFO] " + message);
        }

        public void error(String message) {
            System.out.println("[ERROR] " + message);
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Eager Singleton (AppConfig) ===");
        AppConfig c1 = AppConfig.getInstance();
        AppConfig c2 = AppConfig.getInstance();
        c1.incrementRequestCount();
        c1.incrementRequestCount();
        c2.incrementRequestCount();

        System.out.println("App: " + c1.getAppName() + " [" + c1.getEnvironment() + "]");
        System.out.println("Total requests (via c1): " + c1.getRequestCount());
        System.out.println("Total requests (via c2): " + c2.getRequestCount());
        System.out.println("Same instance? " + (c1 == c2));

        System.out.println();
        System.out.println("=== Lazy Singleton (DatabaseConnection) ===");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        db1.connect();
        System.out.println("db2 connected? " + db2.isConnected());
        System.out.println("Same instance? " + (db1 == db2));

        System.out.println();
        System.out.println("=== Enum Singleton (Logger) ===");
        Logger.INSTANCE.info("Application started");
        Logger.INSTANCE.error("Something went wrong");
        System.out.println("Same instance? " + (Logger.INSTANCE == Logger.INSTANCE));
    }
}
