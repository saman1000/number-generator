package history;

import mega.Config;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class MegaChanceRecordGeneratorTest {

    @Mock
    private Config megaConfig;

    @BeforeEach
    public void initConfig() {
        Mockito.when(megaConfig.getMaxBallNumberValue()).thenReturn(25);
        Mockito.when(megaConfig.getMaxMainNumberValue()).thenReturn(70);
    }

    @Test
    void shouldGenerateOneBallNumber() {
        MegaFrequencyContainer megaFrequencyContainer = new MegaFrequencyContainer(megaConfig);

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(x -> {
                    IntStream.iterate(0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                            .forEach(y -> megaFrequencyContainer.ballNumberDrawn().accept(x))
                    ;
                })
        ;

        MegaChanceRecordGenerator megaChanceRecordGenerator =
                new MegaChanceRecordGenerator(megaFrequencyContainer, new Random());

        int chanceBallNumber = megaChanceRecordGenerator.generateBallNumber();
        assertTrue(chanceBallNumber > 0 && chanceBallNumber < 26);
    }

    void shouldGenerateDifferentBallNumbers() {
        MegaFrequencyContainer megaFrequencyContainer = new MegaFrequencyContainer(megaConfig);

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(x -> {
                    IntStream.iterate(0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                            .forEach(y -> megaFrequencyContainer.ballNumberDrawn().accept(x))
                    ;
                })
        ;

        MegaChanceRecordGenerator megaChanceRecordGenerator =
                new MegaChanceRecordGenerator(megaFrequencyContainer, new Random());

        ArrayList<Integer> generatedBallNumbers =  new ArrayList<>(500);
        for (int counter = Arrays.stream(randomFrequencies).sum(); counter > 0; counter--) {
            int chanceBallNumber = megaChanceRecordGenerator.generateBallNumber();
            assertTrue(chanceBallNumber > 0 && chanceBallNumber < 26);
            generatedBallNumbers.add(chanceBallNumber);
        }

        Map<Integer, Long> chanceFrequencyMap = generatedBallNumbers.parallelStream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        chanceFrequencyMap.forEach((key, value) -> {
            assertTrue(Math.abs(randomFrequencies[key-1] - value) < 100,
                    String.format("random frequency is %s while value is %s", randomFrequencies[key-1], value));
        });
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

        List<Integer> chanceNumbers = megaChanceRecordGenerator.generateMainNumbers();
        assertEquals(5, chanceNumbers.size());
    }

    void shouldGenerateDifferentNumbersSets() {
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

        Map<Integer, Long> generatedMainNumbersFrequency = Collections.synchronizedMap(new HashMap<>());
        IntStream.range(0, Arrays.stream(randomFrequencies).sum())
                .parallel()
                .forEach(x -> {
                    List<Integer> chanceNumbers = megaChanceRecordGenerator.generateMainNumbers();
                    assertEquals(5, chanceNumbers.size());
                    chanceNumbers.stream().forEach(mainNumber -> {
                        generatedMainNumbersFrequency.merge(mainNumber, 1L, Long::sum);
                    });
                });

        generatedMainNumbersFrequency.forEach((key, value) -> {
            int randomFrequency = Math.abs(randomFrequencies[key-1]);
            long actualFrequency = value*5;
            assertTrue(randomFrequency - actualFrequency < 1000,
                    String.format("%s != %s", randomFrequency, actualFrequency)
            );
        });
    }
}