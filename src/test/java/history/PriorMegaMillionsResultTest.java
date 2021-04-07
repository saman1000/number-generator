package history;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * test cases:
 * ) two results on various dates must not be equal even if they contain exactly the same numbers
 * ) two results on the same date must be equal and if they contain different numbers, an error must be thrown
 * )
 */
public class PriorMegaMillionsResultTest {

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void testTwoNonEqualResults1() {
        PriorMegaMillionsResult result1 = new PriorMegaMillionsResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6});
        PriorMegaMillionsResult result2 = new PriorMegaMillionsResult("2010, 12, 22", new Integer[]{1, 2, 3, 4, 5, 6});
        Assertions.assertTrue(!result1.equals(result2));
    }

    @Test
    public final void testTwoEqualResults2() {
        PriorMegaMillionsResult result1 = new PriorMegaMillionsResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6});
        PriorMegaMillionsResult result2 = new PriorMegaMillionsResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6});
        Assertions.assertTrue(result1.equals(result2));
    }

    @ParameterizedTest
    @MethodSource("wrongNumberArrays")
    public void testWrongValues(String date, Integer[] integerArray) {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            new PriorMegaMillionsResult("2010, 12, 29", integerArray);
        });
    }

    private static Stream<Arguments> wrongNumberArrays() {
        return Stream.of(
                Arguments.of("2010, 12, 29", new Integer[] {1, 2, 3, 4, 5, 6, 7}),
                Arguments.of("2010, 12, 29", new Integer[] {1, 2, 3, 4, 5}),
                Arguments.of("2010, 12, 29", new Integer[] {1, 3, 3, 4, 5, 6}),
                Arguments.of("2010, 12, 29", new Integer[0]),
                Arguments.of("2010, 12, 29", null)
        );
    }

    @Test
    public void testAddingTwoResults() {
        ArrayList<Integer> allNumbers = new ArrayList<Integer>(14);
        PriorMegaMillionsResult result1 = new PriorMegaMillionsResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6});
        PriorMegaMillionsResult result2 = new PriorMegaMillionsResult("2010, 12, 22", new Integer[]{1, 2, 3, 4, 5, 6});

        result1.addResults(allNumbers);
        result2.addResults(allNumbers);

        Integer expectedNumberArray[] = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5};
        Collection<Integer> expectedNumbers = Arrays.asList(expectedNumberArray);
        Assertions.assertEquals(expectedNumbers, allNumbers);
    }

    @Test
    public void testDifferentValuesOnSameDate() {
        PriorMegaMillionsResult result1 = new PriorMegaMillionsResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6});
        PriorMegaMillionsResult result2 = new PriorMegaMillionsResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 7});

        Assertions.assertThrows(IllegalStateException.class, () -> {
            result1.equals(result2);
        });
    }
}