package strategy;

public class Example {

    // === Strategy interface ===
    interface ShippingStrategy {
        double calculateFee(double weightKg);
        String getName();
    }

    // === Concrete strategies ===
    static class StandardShipping implements ShippingStrategy {
        public double calculateFee(double weightKg) {
            return 15000 + weightKg * 5000;
        }
        public String getName() { return "Standard"; }
    }

    static class ExpressShipping implements ShippingStrategy {
        public double calculateFee(double weightKg) {
            return 30000 + weightKg * 8000;
        }
        public String getName() { return "Express"; }
    }

    static class SameDayShipping implements ShippingStrategy {
        public double calculateFee(double weightKg) {
            return 50000 + weightKg * 12000;
        }
        public String getName() { return "Same-day"; }
    }

    // === Context: holds one strategy and delegates ===
    static class ShippingCalculator {
        private ShippingStrategy strategy;

        public ShippingCalculator(ShippingStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(ShippingStrategy strategy) {
            this.strategy = strategy;
        }

        public void printFee(String orderId, double weightKg) {
            double fee = strategy.calculateFee(weightKg);
            System.out.printf("  [%s] Order %s (%.1f kg): %,.0f VND%n",
                    strategy.getName(), orderId, weightKg, fee);
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Comparing shipping fees for the same order ===");
        double weight = 2.5;
        ShippingCalculator calc = new ShippingCalculator(new StandardShipping());
        calc.printFee("ORD-001", weight);

        calc.setStrategy(new ExpressShipping());
        calc.printFee("ORD-001", weight);

        calc.setStrategy(new SameDayShipping());
        calc.printFee("ORD-001", weight);

        System.out.println();
        System.out.println("=== Selecting strategy based on conditions ===");
        double[] weights = {0.5, 3.0, 10.0};
        for (double w : weights) {
            ShippingStrategy strategy;
            if (w <= 1.0) {
                strategy = new StandardShipping();
            } else if (w <= 5.0) {
                strategy = new ExpressShipping();
            } else {
                strategy = new SameDayShipping();
            }
            calc.setStrategy(strategy);
            calc.printFee("AUTO", w);
        }

        System.out.println();
        System.out.println("=== Strategy with lambda (Java 8+) ===");
        // Functional interface with a single abstract method
        interface SimpleShipping {
            double calculateFee(double weightKg);
        }

        SimpleShipping freeShipping = weightKg -> 0;
        SimpleShipping flatRate = weightKg -> 20000;

        System.out.printf("  Free shipping (2.5 kg): %,.0f VND%n",
                freeShipping.calculateFee(2.5));
        System.out.printf("  Flat rate (2.5 kg):     %,.0f VND%n",
                flatRate.calculateFee(2.5));
    }
}
