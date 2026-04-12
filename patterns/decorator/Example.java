public class Example {

    interface Drink {
        String description();
        int cost();
    }

    static class Coffee implements Drink {
        public String description() {
            return "Coffee";
        }

        public int cost() {
            return 30000;
        }
    }

    static abstract class DrinkDecorator implements Drink {
        protected final Drink drink;

        protected DrinkDecorator(Drink drink) {
            this.drink = drink;
        }
    }

    static class MilkDecorator extends DrinkDecorator {
        MilkDecorator(Drink drink) {
            super(drink);
        }

        public String description() {
            return drink.description() + ", Milk";
        }

        public int cost() {
            return drink.cost() + 5000;
        }
    }

    static class CaramelDecorator extends DrinkDecorator {
        CaramelDecorator(Drink drink) {
            super(drink);
        }

        public String description() {
            return drink.description() + ", Caramel";
        }

        public int cost() {
            return drink.cost() + 7000;
        }
    }

    static class BobaDecorator extends DrinkDecorator {
        BobaDecorator(Drink drink) {
            super(drink);
        }

        public String description() {
            return drink.description() + ", Boba";
        }

        public int cost() {
            return drink.cost() + 8000;
        }
    }

    public static void main(String[] args) {
        Drink customDrink = new Coffee();
        customDrink = new MilkDecorator(customDrink);
        customDrink = new CaramelDecorator(customDrink);
        customDrink = new BobaDecorator(customDrink);

        System.out.println(customDrink.description());
        System.out.println("Total: " + customDrink.cost());
    }
}
