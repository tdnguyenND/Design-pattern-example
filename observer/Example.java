import java.util.ArrayList;
import java.util.List;

public class Example {

    // === Observer interface ===
    interface OrderObserver {
        void onOrderCreated(String orderId, long amount);
        String getName();
    }

    // === Subject (Publisher) ===
    static class OrderPublisher {
        private final List<OrderObserver> observers = new ArrayList<>();

        public void subscribe(OrderObserver observer) {
            observers.add(observer);
            System.out.println("  [Subscribe] " + observer.getName());
        }

        public void unsubscribe(OrderObserver observer) {
            observers.remove(observer);
            System.out.println("  [Unsubscribe] " + observer.getName());
        }

        public void createOrder(String orderId, long amount) {
            System.out.println("\n>> New order: " + orderId
                    + " (" + String.format("%,d", amount) + " VND)");
            for (OrderObserver observer : observers) {
                observer.onOrderCreated(orderId, amount);
            }
        }

        public int getObserverCount() { return observers.size(); }
    }

    // === Concrete observers ===
    static class WarehouseService implements OrderObserver {
        public void onOrderCreated(String orderId, long amount) {
            System.out.println("   [Warehouse] Preparing shipment for " + orderId);
        }
        public String getName() { return "Warehouse"; }
    }

    static class AccountingService implements OrderObserver {
        public void onOrderCreated(String orderId, long amount) {
            System.out.println("   [Accounting] Created invoice "
                    + String.format("%,d", amount) + " VND for " + orderId);
        }
        public String getName() { return "Accounting"; }
    }

    static class NotificationService implements OrderObserver {
        public void onOrderCreated(String orderId, long amount) {
            System.out.println("   [Notification] Sending confirmation email for " + orderId);
        }
        public String getName() { return "Notification"; }
    }

    static class LoyaltyService implements OrderObserver {
        private int totalPoints = 0;

        public void onOrderCreated(String orderId, long amount) {
            int points = (int) (amount / 10000);
            totalPoints += points;
            System.out.println("   [Loyalty] +" + points
                    + " points (total: " + totalPoints + " points)");
        }
        public String getName() { return "Loyalty"; }
    }

    // === Demo ===
    public static void main(String[] args) {
        OrderPublisher publisher = new OrderPublisher();

        WarehouseService warehouse = new WarehouseService();
        AccountingService accounting = new AccountingService();
        NotificationService notification = new NotificationService();
        LoyaltyService loyalty = new LoyaltyService();

        System.out.println("=== Registering observers ===");
        publisher.subscribe(warehouse);
        publisher.subscribe(accounting);
        publisher.subscribe(notification);
        publisher.subscribe(loyalty);

        publisher.createOrder("ORD-1001", 350000);
        publisher.createOrder("ORD-1002", 1200000);

        System.out.println("\n=== Unsubscribing NotificationService ===");
        publisher.unsubscribe(notification);
        System.out.println("Remaining observers: " + publisher.getObserverCount());

        publisher.createOrder("ORD-1003", 500000);

        System.out.println("\n=== Loyalty retains state across events ===");
        System.out.println("Total accumulated points: "
                + loyalty.totalPoints + " points");
    }
}
