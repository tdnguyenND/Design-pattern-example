public class Example {

    // === Product interface ===
    interface Notification {
        void send(String recipient, String message);
        String getChannel();
    }

    // === Concrete products ===
    static class EmailNotification implements Notification {
        public void send(String recipient, String message) {
            System.out.println("[EMAIL -> " + recipient + "] " + message);
        }
        public String getChannel() { return "Email"; }
    }

    static class SmsNotification implements Notification {
        public void send(String recipient, String message) {
            System.out.println("[SMS -> " + recipient + "] " + message);
        }
        public String getChannel() { return "SMS"; }
    }

    static class PushNotification implements Notification {
        public void send(String recipient, String message) {
            System.out.println("[PUSH -> " + recipient + "] " + message);
        }
        public String getChannel() { return "Push"; }
    }

    // === Creator (abstract) ===
    static abstract class NotificationCreator {
        abstract Notification createNotification();

        // Template method: shared workflow, independent of concrete product
        public void notify(String recipient, String message) {
            Notification notification = createNotification();
            System.out.println("Preparing to send via: " + notification.getChannel());
            notification.send(recipient, message);
            System.out.println("Sent successfully!");
            System.out.println();
        }
    }

    // === Concrete creators ===
    static class EmailCreator extends NotificationCreator {
        Notification createNotification() { return new EmailNotification(); }
    }

    static class SmsCreator extends NotificationCreator {
        Notification createNotification() { return new SmsNotification(); }
    }

    static class PushCreator extends NotificationCreator {
        Notification createNotification() { return new PushNotification(); }
    }

    // === Factory registry: select creator from string config ===
    static NotificationCreator getCreator(String channel) {
        switch (channel.toLowerCase()) {
            case "email": return new EmailCreator();
            case "sms":   return new SmsCreator();
            case "push":  return new PushCreator();
            default: throw new IllegalArgumentException("Unknown channel: " + channel);
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Calling each Creator directly ===");
        NotificationCreator emailCreator = new EmailCreator();
        emailCreator.notify("user@example.com", "Welcome to PatternHub!");

        NotificationCreator smsCreator = new SmsCreator();
        smsCreator.notify("0901234567", "Your OTP is 482910");

        System.out.println("=== Selecting Creator from config (runtime) ===");
        String[] channels = {"email", "sms", "push"};
        for (String channel : channels) {
            NotificationCreator creator = getCreator(channel);
            creator.notify("admin", "System maintenance at 2:00 AM");
        }
    }
}
