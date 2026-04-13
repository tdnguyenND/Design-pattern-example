package abstractfactory;

public class Example {

    // === Abstract products ===
    interface Button {
        void render();
    }

    interface Checkbox {
        void render();
    }

    // === Concrete products: Light theme ===
    static class LightButton implements Button {
        public void render() {
            System.out.println("  [Light Button] white background, dark text");
        }
    }

    static class LightCheckbox implements Checkbox {
        public void render() {
            System.out.println("  [Light Checkbox] outlined style");
        }
    }

    // === Concrete products: Dark theme ===
    static class DarkButton implements Button {
        public void render() {
            System.out.println("  [Dark Button] dark background, light text");
        }
    }

    static class DarkCheckbox implements Checkbox {
        public void render() {
            System.out.println("  [Dark Checkbox] filled style");
        }
    }

    // === Abstract factory ===
    interface UIFactory {
        Button createButton();
        Checkbox createCheckbox();
        String getThemeName();
    }

    // === Concrete factories ===
    static class LightThemeFactory implements UIFactory {
        public Button createButton() { return new LightButton(); }
        public Checkbox createCheckbox() { return new LightCheckbox(); }
        public String getThemeName() { return "Light"; }
    }

    static class DarkThemeFactory implements UIFactory {
        public Button createButton() { return new DarkButton(); }
        public Checkbox createCheckbox() { return new DarkCheckbox(); }
        public String getThemeName() { return "Dark"; }
    }

    // === Client: uses only the abstract factory interface ===
    static class Application {
        private final Button button;
        private final Checkbox checkbox;
        private final String theme;

        Application(UIFactory factory) {
            this.button = factory.createButton();
            this.checkbox = factory.createCheckbox();
            this.theme = factory.getThemeName();
        }

        void renderUI() {
            System.out.println("Rendering " + theme + " theme:");
            button.render();
            checkbox.render();
            System.out.println();
        }
    }

    // === Factory selection from config ===
    static UIFactory getFactory(String theme) {
        switch (theme.toLowerCase()) {
            case "dark":  return new DarkThemeFactory();
            case "light": return new LightThemeFactory();
            default: throw new IllegalArgumentException("Unknown theme: " + theme);
        }
    }

    // === Demo ===
    public static void main(String[] args) {
        System.out.println("=== Creating UI with Light theme ===");
        Application lightApp = new Application(new LightThemeFactory());
        lightApp.renderUI();

        System.out.println("=== Creating UI with Dark theme ===");
        Application darkApp = new Application(new DarkThemeFactory());
        darkApp.renderUI();

        System.out.println("=== Selecting theme from config ===");
        String[] themes = {"light", "dark"};
        for (String theme : themes) {
            UIFactory factory = getFactory(theme);
            new Application(factory).renderUI();
        }
    }
}
