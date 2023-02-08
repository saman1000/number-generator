package history;

import mega.MegaConfig;
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
class MegaChanceRecordGeneratorTest {

    @Mock
    private MegaConfig megaConfig;

    @BeforeEach
    public void initConfig() {
        Mockito.when(megaConfig.getMaxBallNumberValue()).thenReturn(25);
        Mockito.when(megaConfig.getMaxMainNumberValue()).thenReturn(70);
    }

    @Test
    void shouldGenerateTenBallNumbers() {
        MegaFrequencyContainer megaFrequencyContainer = new MegaFrequencyContainer(megaConfig);

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(
                        x -> IntStream.iterate(
                                        0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                                .forEach(y -> megaFrequencyContainer.ballNumberDrawn().accept(x))
                )
        ;

        MegaChanceRecordGenerator megaChanceRecordGenerator =
                new MegaChanceRecordGenerator(megaFrequencyContainer, new Random());

        Integer[] generatedBallNumberSet = megaChanceRecordGenerator.generateBallNumber(ChanceMethod.STRAIGHT, 10);
        Arrays.stream(generatedBallNumberSet)
                        .forEach(
                                ballNumber -> assertTrue(ballNumber > 0 && ballNumber < 26)
                        );
    }

    @Test
    void shouldGenerateExpectedVarianceForBallNumbers() {
        MegaFrequencyContainer megaFrequencyContainer = new MegaFrequencyContainer(megaConfig);

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(x ->
                    IntStream.iterate(0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                            .forEach(y -> megaFrequencyContainer.ballNumberDrawn().accept(x))
                )
        ;

        MegaChanceRecordGenerator megaChanceRecordGenerator =
                new MegaChanceRecordGenerator(megaFrequencyContainer, new Random());

        ArrayList<Integer> generatedBallNumbers =  new ArrayList<>(500);
        for (int counter = Arrays.stream(randomFrequencies).sum(); counter > 0; counter--) {
            int chanceBallNumber = megaChanceRecordGenerator.generateBallNumber(ChanceMethod.STRAIGHT, 1)[0];
            assertTrue(chanceBallNumber > 0 && chanceBallNumber < 26);
            generatedBallNumbers.add(chanceBallNumber);
        }

        Map<Integer, Long> chanceFrequencyMap = generatedBallNumbers.parallelStream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        chanceFrequencyMap.forEach((key, value) ->
            assertTrue(Math.abs(randomFrequencies[key-1] - value) < 100,
                    String.format("random frequency is %s while value is %s", randomFrequencies[key-1], value))
        );
    }

    @Test
    void shouldGenerateOneMainNumbersSet() {
        MegaFrequencyContainer megaFrequencyContainer = new MegaFrequencyContainer(megaConfig);

        int[] randomFrequencies = new Random().ints(70, 1, 5000).toArray();

        for (int counter=0; counter < 70; counter++) {
            int finalCounter = counter;
            IntStream.range(0, randomFrequencies[counter])
                    .forEach( x -> megaFrequencyContainer.mainNumbersDrawn().accept(Stream.of(finalCounter +1)))
            ;
        }

        MegaChanceRecordGenerator megaChanceRecordGenerator =
                new MegaChanceRecordGenerator(megaFrequencyContainer, new Random());

        List<Integer> chanceNumbers = megaChanceRecordGenerator.generateMainNumbers(ChanceMethod.SWAPPED);
        assertEquals(5, chanceNumbers.size());
    }

    @Test
    void shouldGenerateDifferentNumbersSets() {
        MegaFrequencyContainer megaFrequencyContainer = new MegaFrequencyContainer(megaConfig);

        long[] randomFrequencies = new Random().longs(70, 1, 5000).toArray();

        Map<Integer, Long> map = new HashMap<>();
        for (int counter=0; counter < 70; counter++) {
            int finalCounter = counter + 1;
            LongStream.range(0, randomFrequencies[counter])
                    .forEach( x -> megaFrequencyContainer.mainNumbersDrawn().accept(Stream.of(finalCounter)))
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

        MegaChanceRecordGenerator megaChanceRecordGenerator =
                new MegaChanceRecordGenerator(megaFrequencyContainer, new Random());

        Map<Integer, Long> generatedMainNumbersFrequency = Collections.synchronizedMap(new HashMap<>());
        IntStream.range(0, 10000)
                .parallel()
                .forEach(x -> {
                    List<Integer> chanceNumbers = megaChanceRecordGenerator.generateMainNumbers(ChanceMethod.SWAPPED);
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