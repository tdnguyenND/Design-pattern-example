import java.util.ArrayList;
import java.util.List;

public class Example {

    interface OrderObserver {
        void onOrderCreated(String orderId);
    }

    static class OrderPublisher {
        private final List<OrderObserver> observers = new ArrayList<>();

        public void subscribe(OrderObserver observer) {
            observers.add(observer);
        }

        public void unsubscribe(OrderObserver observer) {
            observers.remove(observer);
        }

        public void publishOrder(String orderId) {
            for (OrderObserver observer : observers) {
                observer.onOrderCreated(orderId);
            }
        }
    }

    static class WarehouseDashboard implements OrderObserver {
        public void onOrderCreated(String orderId) {
            System.out.println("Warehouse received order: " + orderId);
        }
    }

    static class AccountingDashboard implements OrderObserver {
        public void onOrderCreated(String orderId) {
            System.out.println("Accounting created invoice for: " + orderId);
        }
    }

    public static void main(String[] args) {
        OrderPublisher publisher = new OrderPublisher();
        publisher.subscribe(new WarehouseDashboard());
        publisher.subscribe(new AccountingDashboard());

        publisher.publishOrder("ORD-1001");
        publisher.publishOrder("ORD-1002");
    }
}
