package facade;

public class Example {

    // === Complex subsystems ===
    static class InventoryService {
        boolean check(String productId, int quantity) {
            System.out.println("  [Inventory] Checking stock for " + productId + " x" + quantity);
            boolean inStock = quantity <= 100;
            System.out.println("  [Inventory] " + (inStock ? "In stock" : "Out of stock"));
            return inStock;
        }

        void reserve(String productId, int quantity) {
            System.out.println("  [Inventory] Reserved " + productId + " x" + quantity);
        }
    }

    static class PaymentService {
        boolean charge(String customerId, double amount) {
            System.out.println("  [Payment] Charging " + customerId + ": $" + String.format("%.2f", amount));
            boolean success = amount <= 10000;
            System.out.println("  [Payment] " + (success ? "Approved" : "Declined"));
            return success;
        }

        void refund(String customerId, double amount) {
            System.out.println("  [Payment] Refunded $" + String.format("%.2f", amount) + " to " + customerId);
        }
    }

    static class ShippingService {
        String ship(String productId, String address) {
            String trackingId = "TRK-" + System.nanoTime() % 100000;
            System.out.println("  [Shipping] Shipping " + productId + " to " + address);
            System.out.println("  [Shipping] Tracking: " + trackingId);
            return trackingId;
        }
    }

    static class NotificationService {
        void sendConfirmation(String customerId, String trackingId) {
            System.out.println("  [Notification] Sent confirmation to " + customerId + " (tracking: " + trackingId + ")");
        }

        void sendFailure(String customerId, String reason) {
            System.out.println("  [Notification] Sent failure notice to " + customerId + ": " + reason);
        }
    }

    // === Facade: simplifies the complex order flow ===
    static class OrderFacade {
        private final InventoryService inventory = new InventoryService();
        private final PaymentService payment = new PaymentService();
        private final ShippingService shipping = new ShippingService();
        private final NotificationService notification = new NotificationService();

        boolean placeOrder(String customerId, String productId, int quantity,
                           double amount, String address) {
            System.out.println("Processing order for " + customerId + "...");

            if (!inventory.check(productId, quantity)) {
                notification.sendFailure(customerId, "Out of stock");
                return false;
            }

            if (!payment.charge(customerId, amount)) {
                notification.sendFailure(customerId, "Payment declined");
                return false;
            }

            inventory.reserve(productId, quantity);
            String trackingId = shipping.ship(productId, address);
            notification.sendConfirmation(customerId, trackingId);

            System.out.println("Order completed successfully!\n");
            return true;
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        OrderFacade facade = new OrderFacade();

        System.out.println("=== Successful order ===");
        facade.placeOrder("customer-1", "LAPTOP-01", 2, 1999.99, "123 Main St");

        System.out.println("=== Failed order: out of stock ===");
        facade.placeOrder("customer-2", "PHONE-01", 999, 499.99, "456 Oak Ave");

        System.out.println("\n=== Failed order: payment declined ===");
        facade.placeOrder("customer-3", "SERVER-01", 1, 99999.99, "789 Pine Rd");
    }
}
