package history;

import games.MegaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class GameChanceRecordGeneratorTest {

    @Mock
    private MegaConfig megaConfig;

    @BeforeEach
    public void initConfig() {
        Mockito.when(megaConfig.getMaxBallNumberValue()).thenReturn(25);
        Mockito.when(megaConfig.getMaxMainNumberValue()).thenReturn(70);
    }

    @Test
    void shouldGenerateTenBallNumbers() {
        GameFrequencyContainer gameFrequencyContainer = new GameFrequencyContainer(megaConfig);

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(
                        x -> IntStream.iterate(
                                        0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                                .forEach(y -> gameFrequencyContainer.ballNumberDrawn().accept(x))
                )
        ;

        IGameChanceRecordGenerator megaChanceRecordGenerator =
                new GameChanceRecordGenerator(gameFrequencyContainer, new Random());

        Integer[] generatedBallNumberSet = megaChanceRecordGenerator.generateBallNumbers(ChanceMethod.STRAIGHT, 10);
        Arrays.stream(generatedBallNumberSet)
                        .forEach(
                                ballNumber -> assertTrue(ballNumber > 0 && ballNumber < 26)
                        );
    }

    @Test
    void shouldGenerateExpectedVarianceForBallNumbers() {
        GameFrequencyContainer gameFrequencyContainer = new GameFrequencyContainer(megaConfig);

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(x ->
                    IntStream.iterate(0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                            .forEach(y -> gameFrequencyContainer.ballNumberDrawn().accept(x))
                )
        ;

        IGameChanceRecordGenerator megaChanceRecordGenerator =
                new GameChanceRecordGenerator(gameFrequencyContainer, new Random());

        Integer[] ballNumbers = megaChanceRecordGenerator.generateBallNumbers(ChanceMethod.STRAIGHT,
                Arrays.stream(randomFrequencies).sum());
        Map<Integer, Long> chanceFrequencyMap = Arrays.stream(ballNumbers)
                .parallel()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        chanceFrequencyMap.forEach((key, value) -> {
            assertTrue(key > 0 && key < 26);
            assertTrue(Math.abs(randomFrequencies[key - 1] - value) < 100,
                    String.format("random frequency is %s while value is %s", randomFrequencies[key - 1], value));
        });
    }

    @Test
    void shouldGenerateExpectedMainNumbersSet() {
        int expectedSets = 10;
        GameFrequencyContainer gameFrequencyContainer = new GameFrequencyContainer(megaConfig);

        int[] randomFrequencies = new Random().ints(70, 1, 5000).toArray();

        for (int counter=0; counter < 70; counter++) {
            int finalCounter = counter;
            IntStream.range(0, randomFrequencies[counter])
                    .forEach( x -> gameFrequencyContainer.mainNumbersDrawn().accept(Stream.of(finalCounter +1)))
            ;
        }

        IGameChanceRecordGenerator megaChanceRecordGenerator =
                new GameChanceRecordGenerator(gameFrequencyContainer, new Random());

        List<Integer>[] generatedNumbers = megaChanceRecordGenerator.generateMainNumbers(ChanceMethod.SWAPPED, expectedSets);
        assertEquals(expectedSets, generatedNumbers.length);
        Arrays.stream(generatedNumbers)
                .forEach(oneMainNumberSet -> assertEquals(5, oneMainNumberSet.size()));
    }

    @Test
    void shouldGenerateDifferentNumbersSets() {
        GameFrequencyContainer gameFrequencyContainer = new GameFrequencyContainer(megaConfig);

        long[] randomFrequencies = new Random().longs(70, 1, 5000).toArray();

        Map<Integer, Long> map = new HashMap<>();
        for (int counter=0; counter < 70; counter++) {
            int finalCounter = counter + 1;
            LongStream.range(0, randomFrequencies[counter])
                    .forEach( x -> gameFrequencyContainer.mainNumbersDrawn().accept(Stream.of(finalCounter)))
            ;
            map.put(finalCounter, randomFrequencies[counter]);
        }

        Comparator<Map.Entry<Integer, Long>> entryComparator = Map.Entry.comparingByValue(Comparator.naturalOrder());
        List<Map.Entry<Integer, Long>> list = map.entrySet().stream()
                .sorted(entryComparator)
                .collect(Collectors.toList());
        HashMap<Integer, Long> swappedMap = new HashMap<>();
        while (list.size() >= 2) {
            Map.Entry<Integer, Long> lowest = list.remove(0);
            Map.Entry<Integer, Long> highest = list.remove(list.size() - 1);
            swappedMap.put(lowest.getKey(), highest.getValue());
            swappedMap.put(highest.getKey(), lowest.getValue());
        }
        if (!list.isEmpty()) {
            Map.Entry<Integer, Long> last = list.remove(0);
            swappedMap.put(last.getKey(), last.getValue());
        }

        IGameChanceRecordGenerator megaChanceRecordGenerator =
                new GameChanceRecordGenerator(gameFrequencyContainer, new Random());

        Map<Integer, Long> generatedMainNumbersFrequency = Collections.synchronizedMap(new HashMap<>());
        IntStream.range(0, 10000)
                .parallel()
                .forEach(x -> {
                    List<Integer> chanceNumbers = megaChanceRecordGenerator.generateMainNumbers(ChanceMethod.SWAPPED, 1)[0];
                    assertEquals(5, chanceNumbers.size());
                    chanceNumbers.forEach(mainNumber ->
                        generatedMainNumbersFrequency.merge(mainNumber, 1L, Long::sum)
                    );
                });

        generatedMainNumbersFrequency.forEach((key, actualFrequency) -> {
            if (!swappedMap.containsKey(key)) {
                System.out.println();
            }
            long randomFrequency = swappedMap.get(key);
            assertTrue(actualFrequency - randomFrequency < 1000,
                    String.format("%s != %s for %s", randomFrequency, actualFrequency, key)
            );
        });
    }
}