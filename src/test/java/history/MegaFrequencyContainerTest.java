package history;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class MegaFrequencyContainerTest {

    @Test
    void getBallNumberFrequency() {
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

        for (int counter = 0; counter < randomFrequencies.length; counter++) {
            Assertions.assertEquals(
                    randomFrequencies[counter]+1, megaFrequencyContainer.getBallNumberFrequency(counter+1)
            );
        }
    }

    @Test
    void getMainNumberFrequency() {
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

        for (int counter = 0; counter < randomFrequencies.length; counter++) {
            for (int mainNumberCounter = counter*5+1; mainNumberCounter <= counter*5+5; mainNumberCounter++) {
                Assertions.assertEquals(
                        randomFrequencies[counter]+1,
                        megaFrequencyContainer.getMainNumberFrequency(mainNumberCounter));
            }
        }
    }
}