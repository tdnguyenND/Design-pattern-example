public class Example {

    // === Component interface ===
    interface Drink {
        String description();
        int cost();
    }

    // === Concrete components (base drinks) ===
    static class Coffee implements Drink {
        public String description() { return "Coffee"; }
        public int cost() { return 30000; }
    }

    static class GreenTea implements Drink {
        public String description() { return "Green Tea"; }
        public int cost() { return 25000; }
    }

    // === Base decorator ===
    static abstract class DrinkDecorator implements Drink {
        protected final Drink drink;
        protected DrinkDecorator(Drink drink) {
            this.drink = drink;
        }
    }

    // === Concrete decorators (toppings) ===
    static class MilkDecorator extends DrinkDecorator {
        MilkDecorator(Drink drink) { super(drink); }
        public String description() { return drink.description() + " + Milk"; }
        public int cost() { return drink.cost() + 5000; }
    }

    static class CaramelDecorator extends DrinkDecorator {
        CaramelDecorator(Drink drink) { super(drink); }
        public String description() { return drink.description() + " + Caramel"; }
        public int cost() { return drink.cost() + 7000; }
    }

    static class BobaDecorator extends DrinkDecorator {
        BobaDecorator(Drink drink) { super(drink); }
        public String description() { return drink.description() + " + Boba"; }
        public int cost() { return drink.cost() + 8000; }
    }

    static class WhipCreamDecorator extends DrinkDecorator {
        WhipCreamDecorator(Drink drink) { super(drink); }
        public String description() { return drink.description() + " + Whip Cream"; }
        public int cost() { return drink.cost() + 6000; }
    }

    // === Helper: print drink info ===
    static void printDrink(String label, Drink drink) {
        System.out.printf("  %-12s %-45s %,8d VND%n", label, drink.description(), drink.cost());
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Step-by-step decoration ===");
        Drink drink = new Coffee();
        printDrink("Base:", drink);

        drink = new MilkDecorator(drink);
        printDrink("+Milk:", drink);

        drink = new CaramelDecorator(drink);
        printDrink("+Caramel:", drink);

        drink = new BobaDecorator(drink);
        printDrink("+Boba:", drink);

        System.out.println();
        System.out.println("=== Multiple combos from 2 base drinks ===");

        Drink combo1 = new WhipCreamDecorator(new MilkDecorator(new Coffee()));
        printDrink("Combo 1:", combo1);

        Drink combo2 = new BobaDecorator(new MilkDecorator(new GreenTea()));
        printDrink("Combo 2:", combo2);

        Drink combo3 = new WhipCreamDecorator(
                new CaramelDecorator(
                        new BobaDecorator(
                                new MilkDecorator(new Coffee()))));
        printDrink("Full:", combo3);

        System.out.println();
        System.out.println("=== Double topping (same decorator twice) ===");
        Drink doubleMilk = new MilkDecorator(new MilkDecorator(new GreenTea()));
        printDrink("2x Milk:", doubleMilk);
    }
}
