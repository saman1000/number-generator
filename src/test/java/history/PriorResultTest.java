package history;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * test cases:
 * ) two results on various dates must not be equal even if they contain exactly the same numbers
 * ) two results on the same date must be equal and if they contain different numbers, an error must be thrown
 * )
 */
public class PriorResultTest {

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
        new PriorResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6, 7});
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public final void testTwoNonEqualResults1() {
        PriorResult result1 = new PriorResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6, 7});
        PriorResult result2 = new PriorResult("2010, 12, 22", new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Assertions.assertTrue(!result1.equals(result2));
    }

    @Test
    public final void testTwoEqualResults2() {
        PriorResult result1 = new PriorResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6, 7});
        PriorResult result2 = new PriorResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Assertions.assertTrue(result1.equals(result2));
    }

    @Test
    public final void testWrongValues() {
        boolean notPassed = false;
        try {
            new PriorResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6, 6});
            notPassed = true;
        } catch (IllegalStateException ex) {
        }

        if (notPassed) {
            Assertions.fail("wrong values were accepted");
        }
    }

    @Test
    public final void testAddingTwoResults() {
        ArrayList<Integer> allNumbers = new ArrayList<Integer>(14);
        PriorResult result1 = new PriorResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6, 7});
        PriorResult result2 = new PriorResult("2010, 12, 22", new Integer[]{1, 2, 3, 4, 5, 6, 7});

        result1.addResults(allNumbers);
        result2.addResults(allNumbers);

        Integer expectedNumberArray[] = {1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7};
        Collection<Integer> expectedNumbers = Arrays.asList(expectedNumberArray);
        Assertions.assertTrue(expectedNumbers.equals(allNumbers));
    }

    @Test
    public final void testDifferentValuesOnSameDate() {
        boolean notPassed = false;
        try {
            PriorResult result1 = new PriorResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6, 7});
            PriorResult result2 = new PriorResult("2010, 12, 29", new Integer[]{1, 2, 3, 4, 5, 6, 8});
            result1.equals(result2);
            notPassed = true;
        } catch (IllegalStateException ex) {
        }

        if (notPassed) {
            Assertions.fail("different values for the same date");
        }
    }
}