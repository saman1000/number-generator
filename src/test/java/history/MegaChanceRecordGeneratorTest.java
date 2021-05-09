package history;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MegaChanceRecordGeneratorTest {

    @Test
    void generateBallNumber() {
        MegaFrequencyContainer megaFrequencyContainer = new MegaFrequencyContainer(70, 25);

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(x -> {
                    IntStream.iterate(0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                            .forEach(y -> megaFrequencyContainer.ballNumberConsumer().accept(x))
                    ;
                })
        ;

        MegaChanceRecordGenerator megaChanceRecordGenerator = new MegaChanceRecordGenerator(megaFrequencyContainer);

        int chanceBallNumber = megaChanceRecordGenerator.generateBallNumber();
        assertTrue(chanceBallNumber > 0 && chanceBallNumber < 26);
    }

    @Test
    void generateMainNumbers() {
        MegaFrequencyContainer megaFrequencyContainer = new MegaFrequencyContainer(70, 25);

        int[] randomFrequencies = new Random().ints(70 / 5, 1, 5000).toArray();

        IntStream.range(1, 70 / 5 + 1)
                .parallel()
                .forEach(x -> {
                    IntStream.range(0, randomFrequencies[x - 1])
                            .forEach(y -> megaFrequencyContainer
                                    .mainNumbersConsumer()
                                    .accept(
                                            Stream.iterate(
                                                    x * 5, counter -> counter > x * 5 - 5, counter -> counter - 1)
                                    ));
                })
        ;

        MegaChanceRecordGenerator megaChanceRecordGenerator = new MegaChanceRecordGenerator(megaFrequencyContainer);

        List<Integer> chanceNumbers = megaChanceRecordGenerator.generateMainNumbers();
        assertEquals(5, chanceNumbers.size());
    }
}