public class Example {

    interface Notification {
        void send(String message);
    }

    static class EmailNotification implements Notification {
        public void send(String message) {
            System.out.println("Email sent: " + message);
        }
    }

    static class SmsNotification implements Notification {
        public void send(String message) {
            System.out.println("SMS sent: " + message);
        }
    }

    static abstract class NotificationCreator {
        abstract Notification createNotification();

        public void send(String message) {
            Notification notification = createNotification();
            notification.send(message);
        }
    }

    static class EmailCreator extends NotificationCreator {
        Notification createNotification() {
            return new EmailNotification();
        }
    }

    static class SmsCreator extends NotificationCreator {
        Notification createNotification() {
            return new SmsNotification();
        }
    }

    public static void main(String[] args) {
        NotificationCreator email = new EmailCreator();
        NotificationCreator sms = new SmsCreator();

        email.send("Welcome to our platform!");
        sms.send("Your OTP is 123456");
    }
}
