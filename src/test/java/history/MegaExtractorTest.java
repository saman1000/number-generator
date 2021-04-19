package history;

import jdk.dynalink.beans.StaticClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MegaExtractorTest {

    private static Stream<Arguments> validExpectedResults() {
        return Stream.of(
                Arguments.of(new String[] {"3/19/2021", "9,14,40,58,69", "Mega Ball: 8"}, "3/19/2021", Arrays.asList(9, 14, 40, 58, 69), 8),
                Arguments.of(new String[] {"3/19/2021", "9,14,58,40,69", "Mega Ball: 8"}, "3/19/2021", Arrays.asList(9, 14, 40, 58, 69), 8),
                Arguments.of(new String[] {"3/19/2021", "1,2,3,4,5", "Mega Ball: 5"}, "3/19/2021", Arrays.asList(1, 2, 3, 4, 5), 5)
        );
    }

    @ParameterizedTest
    @MethodSource("validExpectedResults")
    void shouldExtractOneResult(String[] parameters, String date, List<Integer> numbers, Integer ballNumber) {
        MegaExtractor megaExtractor = new MegaExtractor();

        PriorMegaMillionsResult priorMegaMillionsResult =
                megaExtractor.extractResult(parameters);
        assertNotNull(priorMegaMillionsResult);

        assertEquals(date, priorMegaMillionsResult.getDrawnDate());
        assertEquals(numbers, priorMegaMillionsResult.getNumbers());
        assertEquals(ballNumber, priorMegaMillionsResult.getMegaBallNumber());
    }

    private static Stream<Arguments> invalidExpectedResults() {
        return Stream.of(
                Arguments.arguments((Object) new String[]{"3/19/2021", "9,14,40,40,69", "Mega Ball: 8"}),
                Arguments.of((Object) new String[] {"3/19/2021", "", "Mega Ball: 8"}),
                Arguments.of((Object) new String[] {"3/19/2021", "1,2,3,4,5", ""})
        );
    }

    @ParameterizedTest
    @MethodSource("invalidExpectedResults")
    void shouldFailToExtractOneResult(String[] parameters) {
        MegaExtractor megaExtractor = new MegaExtractor();

        Assertions.assertThrows(IllegalStateException.class, () -> {
            megaExtractor.extractResult(parameters);
        });
    }
}