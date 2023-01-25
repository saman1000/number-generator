package history;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NavigableMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MegaFrequencyTest {

    @Test
    void testFrequencyOfNumber() {
        MegaFrequency megaFrequency = new MegaFrequency(10);
        Integer[] testNumbers = new Integer[10];
        IntStream.range(1, 11)
                .forEach(x -> testNumbers[x-1]=x*10);

        for (int counter = 1; counter <= 10; counter++) {
            for (int numberFrequency=testNumbers[counter-1]; numberFrequency > 0; numberFrequency--) {
                megaFrequency.numberOccurrenceObserved(counter);
            }
        }

        for (int counter=0; counter < 10; counter++) {
            assertEquals(testNumbers[counter]+1, megaFrequency.getFrequencyOfNumber(counter+1));
        }
    }

    @Test
    void testInvalidNumberShouldThrowInvalidMegaNumberException() {
        MegaFrequency megaFrequency = new MegaFrequency(10);

        assertThrows(InvalidMegaNumberException.class, () -> megaFrequency.numberOccurrenceObserved(11));
    }

    private static Stream<Arguments> chanceMapInputOutput() {
        return Stream.of(
                Arguments.of(3, new Integer[] {1, 1, 1}, new Integer[] {2, 4, 6}),
                Arguments.of(3, new Integer[] {1, 0, 1}, new Integer[] {2, 3, 5}),
                Arguments.of(3, new Integer[] {1, 2, 1}, new Integer[] {2, 5, 7}),
                Arguments.of(3, new Integer[] {9, 14, 39}, new Integer[] {10, 25, 65}),
                Arguments.of(3, new Integer[] {14, 9, 39}, new Integer[] {15, 25, 65}),
                Arguments.of(5, new Integer[] {9, 14, 40, 58, 69}, new Integer[] {10, 25, 66, 125, 195}),
                Arguments.of(10,
                        new Integer[] {1, 2, 3, 1, 2, 3, 2, 1, 30, 10},
                        new Integer[] {2, 5, 9, 11, 14, 18, 21, 23, 54, 65})
        );
    }

    @ParameterizedTest
    @MethodSource("chanceMapInputOutput")
    void testChanceMap(int mainNumbersLength, Integer[] frequencyArray, Integer[] expectedChance) {
        MegaFrequency megaFrequency = new MegaFrequency(mainNumbersLength);

        IntStream.range(1, mainNumbersLength+1)
                .parallel()
                .forEach(x -> {
                    for (int counter = frequencyArray[x-1]; counter > 0; counter--) {
                        megaFrequency.numberOccurrenceObserved(x);
                    }
                });
        NavigableMap<Integer, Integer> chanceMap = megaFrequency.getChanceMap();
        assertNotNull(chanceMap);
        chanceMap.entrySet().stream()
                .parallel()
                .forEach(entry -> assertEquals(expectedChance[entry.getValue()-1], entry.getKey()));
    }

    private static Stream<Arguments> chanceMapInputOutput2() {
        return Stream.of(
                Arguments.of(3, new Integer[] {1, 1, 1}, new Integer[] {2, 4, 6}),
                Arguments.of(3, new Integer[] {1, 0, 1}, new Integer[] {1, 3, 4}),
                Arguments.of(3, new Integer[] {1, 2, 1}, new Integer[] {3, 5, 8}),
                Arguments.of(3, new Integer[] {9, 14, 39}, new Integer[] {40, 55, 65}),

                Arguments.of(3, new Integer[] {14, 9, 39}, new Integer[] {15, 55, 65}),
                Arguments.of(5, new Integer[] {9, 14, 40, 58, 69}, new Integer[] {70, 129, 170, 185, 195}),
                Arguments.of(10,
                        new Integer[] {1, 2, 3, 1, 2, 3, 2, 1, 30, 10},
                        new Integer[] {31, 42, 46, 77, 88, 92, 103, 134, 136, 139})
        );
    }

    @ParameterizedTest
    @MethodSource("chanceMapInputOutput2")
    void testSwappedChanceMap(int mainNumbersLength, final Integer[] frequencyArray, final Integer[] expectedChance) {
        MegaFrequency megaFrequency = new MegaFrequency(mainNumbersLength);

        IntStream.range(1, mainNumbersLength + 1)
                .parallel()
                .forEach(x -> {
                    for (int counter = frequencyArray[x - 1]; counter > 0; counter--) {
                        megaFrequency.numberOccurrenceObserved(x);
                    }
                });
        NavigableMap<Integer, Integer> chanceMap = megaFrequency.getSwappedChanceMap();
        assertNotNull(chanceMap);
        chanceMap.entrySet().stream()
                .parallel()
                .forEach(entry -> assertEquals(expectedChance[entry.getValue()-1], entry.getKey()));

    }
}