public class Example {

    interface PaymentGateway {
        void pay(long amountVnd);
    }

    static class LegacyPaymentSdk {
        public void makeLegacyPayment(String amountText) {
            System.out.println("Legacy SDK paid: " + amountText);
        }
    }

    static class LegacyPaymentAdapter implements PaymentGateway {
        private final LegacyPaymentSdk legacyPaymentSdk;

        public LegacyPaymentAdapter(LegacyPaymentSdk legacyPaymentSdk) {
            this.legacyPaymentSdk = legacyPaymentSdk;
        }

        public void pay(long amountVnd) {
            legacyPaymentSdk.makeLegacyPayment(amountVnd + " VND");
        }
    }

    static class CheckoutService {
        private final PaymentGateway gateway;

        public CheckoutService(PaymentGateway gateway) {
            this.gateway = gateway;
        }

        public void checkout(long amountVnd) {
            gateway.pay(amountVnd);
        }
    }

    public static void main(String[] args) {
        PaymentGateway gateway = new LegacyPaymentAdapter(new LegacyPaymentSdk());
        CheckoutService service = new CheckoutService(gateway);

        service.checkout(199000);
    }
}
