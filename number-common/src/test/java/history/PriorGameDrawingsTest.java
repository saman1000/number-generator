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
 * 1) two results on various dates must not be equal even if they contain exactly the same numbers
 * 2) two results on the same date must be equal and if they contain different numbers, an error must be thrown
 * 3)
 */
public class PriorGameDrawingsTest {

    @Test
    public void testTwoEqualResults() {
        PriorGameDrawings result1 =
                new PriorGameDrawings("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 6);
        PriorGameDrawings result2 =
                new PriorGameDrawings("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 6);
        Assertions.assertEquals(result1, result2);
    }

    @Test
    public void testTwoNonEqualResults() {
        PriorGameDrawings result1 =
                new PriorGameDrawings("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 6);
        PriorGameDrawings result2 =
                new PriorGameDrawings("2010, 12, 22", Arrays.asList(1, 2, 3, 4, 5), 6);
        Assertions.assertNotEquals(result1, result2);
    }

    @ParameterizedTest
    @MethodSource("wrongNumberArrays")
    public void testWrongValues(String date, List<Integer> numberStream, Integer oneInteger) {
        Assertions.assertThrows(IllegalStateException.class, () ->
            new PriorGameDrawings(date, numberStream, oneInteger)
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
    public void testDifferentValuesOnSameDate() {
        PriorGameDrawings result1 =
                new PriorGameDrawings("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 6);
        PriorGameDrawings result2 =
                new PriorGameDrawings("2010, 12, 29", Arrays.asList(1, 2, 3, 4, 5), 7);

        Assertions.assertThrows(IllegalStateException.class, () ->
            result1.equals(result2)
        );
    }
}