package proxy;

import java.util.HashMap;
import java.util.Map;

public class Example {

    // === Subject interface ===
    interface ImageLoader {
        String load(String path);
    }

    // === Real subject: expensive operation ===
    static class DiskImageLoader implements ImageLoader {
        public String load(String path) {
            System.out.println("  [Disk] Loading from disk: " + path + " (slow)");
            // Simulates expensive I/O
            return "image-data-of(" + path + ")";
        }
    }

    // === Proxy 1: Caching proxy ===
    static class CachingImageProxy implements ImageLoader {
        private final ImageLoader realLoader;
        private final Map<String, String> cache = new HashMap<>();

        CachingImageProxy(ImageLoader realLoader) {
            this.realLoader = realLoader;
        }

        public String load(String path) {
            if (cache.containsKey(path)) {
                System.out.println("  [Cache] HIT for: " + path);
                return cache.get(path);
            }
            System.out.println("  [Cache] MISS for: " + path);
            String data = realLoader.load(path);
            cache.put(path, data);
            return data;
        }

        int cacheSize() { return cache.size(); }
    }

    // === Proxy 2: Access control proxy ===
    static class AccessControlProxy implements ImageLoader {
        private final ImageLoader realLoader;
        private final String allowedRole;
        private final String currentRole;

        AccessControlProxy(ImageLoader realLoader, String currentRole, String allowedRole) {
            this.realLoader = realLoader;
            this.currentRole = currentRole;
            this.allowedRole = allowedRole;
        }

        public String load(String path) {
            if (!currentRole.equals(allowedRole)) {
                System.out.println("  [Access] DENIED for role '" + currentRole
                        + "' (requires '" + allowedRole + "')");
                return null;
            }
            System.out.println("  [Access] GRANTED for role '" + currentRole + "'");
            return realLoader.load(path);
        }
    }

    // === Proxy 3: Logging proxy ===
    static class LoggingImageProxy implements ImageLoader {
        private final ImageLoader realLoader;
        private int callCount = 0;

        LoggingImageProxy(ImageLoader realLoader) {
            this.realLoader = realLoader;
        }

        public String load(String path) {
            callCount++;
            System.out.println("  [Log] Call #" + callCount + ": load(\"" + path + "\")");
            String result = realLoader.load(path);
            System.out.println("  [Log] Result: " + (result != null ? "OK" : "null"));
            return result;
        }

        int getCallCount() { return callCount; }
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Caching Proxy ===");
        CachingImageProxy cachingProxy = new CachingImageProxy(new DiskImageLoader());
        cachingProxy.load("photo.jpg");
        cachingProxy.load("banner.png");
        cachingProxy.load("photo.jpg");
        cachingProxy.load("photo.jpg");
        System.out.println("Cache size: " + cachingProxy.cacheSize());

        System.out.println();
        System.out.println("=== Access Control Proxy ===");
        ImageLoader adminLoader = new AccessControlProxy(new DiskImageLoader(), "admin", "admin");
        adminLoader.load("secret.png");

        System.out.println();
        ImageLoader guestLoader = new AccessControlProxy(new DiskImageLoader(), "guest", "admin");
        guestLoader.load("secret.png");

        System.out.println();
        System.out.println("=== Logging Proxy ===");
        LoggingImageProxy loggingProxy = new LoggingImageProxy(new DiskImageLoader());
        loggingProxy.load("icon.svg");
        loggingProxy.load("logo.png");
        System.out.println("Total calls: " + loggingProxy.getCallCount());

        System.out.println();
        System.out.println("=== Composing proxies: Logging + Caching ===");
        LoggingImageProxy composed = new LoggingImageProxy(
                new CachingImageProxy(new DiskImageLoader()));
        composed.load("combo.jpg");
        composed.load("combo.jpg");
    }
}
