public class Example {

    interface ShippingStrategy {
        double calculateFee(double weightKg);
    }

    static class StandardShipping implements ShippingStrategy {
        public double calculateFee(double weightKg) {
            return 15000 + weightKg * 5000;
        }
    }

    static class ExpressShipping implements ShippingStrategy {
        public double calculateFee(double weightKg) {
            return 30000 + weightKg * 8000;
        }
    }

    static class SameDayShipping implements ShippingStrategy {
        public double calculateFee(double weightKg) {
            return 50000 + weightKg * 12000;
        }
    }

    static class ShippingCalculator {
        private ShippingStrategy strategy;

        public ShippingCalculator(ShippingStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(ShippingStrategy strategy) {
            this.strategy = strategy;
        }

        public double calculate(double weightKg) {
            return strategy.calculateFee(weightKg);
        }
    }

    public static void main(String[] args) {
        double orderWeight = 2.5;
        ShippingCalculator calculator = new ShippingCalculator(new StandardShipping());

        System.out.println("Standard: " + calculator.calculate(orderWeight));

        calculator.setStrategy(new ExpressShipping());
        System.out.println("Express: " + calculator.calculate(orderWeight));

        calculator.setStrategy(new SameDayShipping());
        System.out.println("Same day: " + calculator.calculate(orderWeight));
    }
}
