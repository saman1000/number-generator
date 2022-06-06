package history;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

/**
 * test cases:
 * ) two results on various dates must not be equal even if they contain exactly the same numbers
 * ) two results on the same date must be equal and if they contain different numbers, an error must be thrown
 * )
 */
public class PriorMegaMillionsResultTest {

    @Test
    public void testTwoEqualResults() {
        PriorMegaMillionsResult result1 =
                new PriorMegaMillionsResult("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 6);
        PriorMegaMillionsResult result2 =
                new PriorMegaMillionsResult("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 6);
        Assertions.assertEquals(result1, result2);
    }

    @Test
    public void testTwoNonEqualResults() {
        PriorMegaMillionsResult result1 =
                new PriorMegaMillionsResult("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 6);
        PriorMegaMillionsResult result2 =
                new PriorMegaMillionsResult("2010, 12, 22", Arrays.asList(1, 2, 3, 4, 5), 6);
        Assertions.assertNotEquals(result1, result2);
    }

    @ParameterizedTest
    @MethodSource("wrongNumberArrays")
    public void testWrongValues(String date, List<Integer> numberStream, Integer oneInteger) {
        Assertions.assertThrows(IllegalStateException.class, () ->
            new PriorMegaMillionsResult("2010, 12, 29", numberStream, oneInteger)
        );
    }

    private static Stream<Arguments> wrongNumberArrays() {
        return Stream.of(
                Arguments.of("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5, 6), 7),
                Arguments.of("2010, 12, 29", Arrays.asList(1, 2, 3, 4), 7),
                Arguments.of("2010, 12, 29", Collections.emptyList(), 7)
        );
    }

    @Test
    public void testAddingTwoResults() {
        ArrayList<Integer> allNumbers = new ArrayList<>(14);
        PriorMegaMillionsResult result1 =
                new PriorMegaMillionsResult("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 6);
        PriorMegaMillionsResult result2 =
                new PriorMegaMillionsResult("2010, 12, 22", Arrays.asList(1, 2, 3, 4, 5), 6);

        result1.addResults(allNumbers);
        result2.addResults(allNumbers);

        Integer[] expectedNumberArray = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5};
        Collection<Integer> expectedNumbers = Arrays.asList(expectedNumberArray);
        Assertions.assertEquals(expectedNumbers, allNumbers);
    }

    @Test
    public void testDifferentValuesOnSameDate() {
        PriorMegaMillionsResult result1 =
                new PriorMegaMillionsResult("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 6);
        PriorMegaMillionsResult result2 =
                new PriorMegaMillionsResult("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 7);

        Assertions.assertThrows(IllegalStateException.class, () ->
            result1.equals(result2)
        );
    }
}