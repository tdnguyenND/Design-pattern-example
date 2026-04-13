import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ExampleSmokeTest {

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {
        "singleton.Example",
        "factorymethod.Example",
        "builder.Example",
        "adapter.Example",
        "decorator.Example",
        "composite.Example",
        "strategy.Example",
        "observer.Example",
        "abstractfactory.Example",
        "prototype.Example",
        "facade.Example",
        "proxy.Example",
        "command.Example",
        "templatemethod.Example",
        "state.Example",
        "chain.Example"
    })
    void mainRunsWithoutException(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        Method main = clazz.getMethod("main", String[].class);

        PrintStream original = System.out;
        ByteArrayOutputStream capture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capture));
        try {
            main.invoke(null, (Object) new String[]{});
        } finally {
            System.setOut(original);
        }

        String output = capture.toString();
        assertFalse(output.isEmpty(), className + " should produce output");
    }
}
