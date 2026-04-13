package state;

public class Example {

    // === State interface ===
    interface OrderState {
        void next(Order order);
        void cancel(Order order);
        String getName();
    }

    // === Context ===
    static class Order {
        private final String orderId;
        private OrderState state;

        Order(String orderId) {
            this.orderId = orderId;
            this.state = new PendingState();
        }

        void setState(OrderState state) {
            System.out.println("  [" + orderId + "] " + this.state.getName() + " -> " + state.getName());
            this.state = state;
        }

        void next() {
            System.out.println("  [" + orderId + "] Action: next (current: " + state.getName() + ")");
            state.next(this);
        }

        void cancel() {
            System.out.println("  [" + orderId + "] Action: cancel (current: " + state.getName() + ")");
            state.cancel(this);
        }

        String getStateName() { return state.getName(); }
        String getOrderId() { return orderId; }
    }

    // === Concrete states ===
    static class PendingState implements OrderState {
        public void next(Order order) { order.setState(new ConfirmedState()); }
        public void cancel(Order order) { order.setState(new CancelledState()); }
        public String getName() { return "Pending"; }
    }

    static class ConfirmedState implements OrderState {
        public void next(Order order) { order.setState(new ShippedState()); }
        public void cancel(Order order) { order.setState(new CancelledState()); }
        public String getName() { return "Confirmed"; }
    }

    static class ShippedState implements OrderState {
        public void next(Order order) { order.setState(new DeliveredState()); }
        public void cancel(Order order) {
            System.out.println("  [" + order.getOrderId() + "] Cannot cancel: already shipped");
        }
        public String getName() { return "Shipped"; }
    }

    static class DeliveredState implements OrderState {
        public void next(Order order) {
            System.out.println("  [" + order.getOrderId() + "] Already delivered: no next state");
        }
        public void cancel(Order order) {
            System.out.println("  [" + order.getOrderId() + "] Cannot cancel: already delivered");
        }
        public String getName() { return "Delivered"; }
    }

    static class CancelledState implements OrderState {
        public void next(Order order) {
            System.out.println("  [" + order.getOrderId() + "] Cannot proceed: order is cancelled");
        }
        public void cancel(Order order) {
            System.out.println("  [" + order.getOrderId() + "] Already cancelled");
        }
        public String getName() { return "Cancelled"; }
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Happy path: Pending -> Confirmed -> Shipped -> Delivered ===");
        Order order1 = new Order("ORD-001");
        order1.next();
        order1.next();
        order1.next();
        order1.next();

        System.out.println();
        System.out.println("=== Cancel from Pending ===");
        Order order2 = new Order("ORD-002");
        order2.cancel();
        order2.next();

        System.out.println();
        System.out.println("=== Cancel from Confirmed ===");
        Order order3 = new Order("ORD-003");
        order3.next();
        order3.cancel();

        System.out.println();
        System.out.println("=== Cannot cancel after Shipped ===");
        Order order4 = new Order("ORD-004");
        order4.next();
        order4.next();
        order4.cancel();
    }
}
