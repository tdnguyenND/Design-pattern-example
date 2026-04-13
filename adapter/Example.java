public class Example {

    // === Target interface ===
    interface PaymentGateway {
        void pay(long amountVnd);
        String getName();
    }

    // === Adaptee: legacy SDK with incompatible interface ===
    static class LegacyPaymentSdk {
        public void makeLegacyPayment(String amountText) {
            System.out.println("  [Legacy SDK] Payment: " + amountText);
        }
    }

    // === Adapter: converts LegacyPaymentSdk -> PaymentGateway ===
    static class LegacyPaymentAdapter implements PaymentGateway {
        private final LegacyPaymentSdk legacySdk;

        public LegacyPaymentAdapter(LegacyPaymentSdk legacySdk) {
            this.legacySdk = legacySdk;
        }

        public void pay(long amountVnd) {
            String formatted = String.format("%,d VND", amountVnd);
            legacySdk.makeLegacyPayment(formatted);
        }

        public String getName() { return "Legacy Bank"; }
    }

    // === Modern implementations: directly compatible ===
    static class MomoGateway implements PaymentGateway {
        public void pay(long amountVnd) {
            System.out.println("  [MoMo] Payment: " + String.format("%,d VND", amountVnd));
        }
        public String getName() { return "MoMo"; }
    }

    static class VnPayGateway implements PaymentGateway {
        public void pay(long amountVnd) {
            System.out.println("  [VNPay] Payment: " + String.format("%,d VND", amountVnd));
        }
        public String getName() { return "VNPay"; }
    }

    // === Client: only knows PaymentGateway, agnostic of legacy vs modern ===
    static class CheckoutService {
        private final PaymentGateway gateway;

        public CheckoutService(PaymentGateway gateway) {
            this.gateway = gateway;
        }

        public void checkout(String orderId, long amountVnd) {
            System.out.println("Order " + orderId + " - using " + gateway.getName() + ":");
            gateway.pay(amountVnd);
            System.out.println("  Done!");
            System.out.println();
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Adapter: wrapping the Legacy SDK ===");
        PaymentGateway legacyAdapter = new LegacyPaymentAdapter(new LegacyPaymentSdk());
        CheckoutService legacyCheckout = new CheckoutService(legacyAdapter);
        legacyCheckout.checkout("ORD-001", 199000);

        System.out.println("=== Modern gateways: directly compatible ===");
        CheckoutService momoCheckout = new CheckoutService(new MomoGateway());
        momoCheckout.checkout("ORD-002", 350000);

        CheckoutService vnpayCheckout = new CheckoutService(new VnPayGateway());
        vnpayCheckout.checkout("ORD-003", 1250000);

        System.out.println("=== All share the same PaymentGateway interface ===");
        PaymentGateway[] gateways = {
            new LegacyPaymentAdapter(new LegacyPaymentSdk()),
            new MomoGateway(),
            new VnPayGateway()
        };
        for (PaymentGateway gw : gateways) {
            System.out.println("- " + gw.getName());
        }
    }
}
