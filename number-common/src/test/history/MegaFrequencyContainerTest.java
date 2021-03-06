package history;

import mega.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class MegaFrequencyContainerTest {


    @Mock
    private Config megaConfig;

    @BeforeEach
    public void initConfig() {
        Mockito.when(megaConfig.getMaxBallNumberValue()).thenReturn(25);
        Mockito.when(megaConfig.getMaxMainNumberValue()).thenReturn(70);
    }

    @Test
    void getBallNumberFrequency() {
        MegaFrequencyContainer megaFrequencyContainer = new MegaFrequencyContainer(megaConfig);

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(x ->
                    IntStream.iterate(0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                            .forEach(y -> megaFrequencyContainer.ballNumberDrawn().accept(x))
                )
        ;

        for (int counter = 0; counter < randomFrequencies.length; counter++) {
            Assertions.assertEquals(
                    randomFrequencies[counter]+1, megaFrequencyContainer.getBallNumberFrequency(counter+1)
            );
        }
    }

    @Test
    void getMainNumberFrequency() {
        MegaFrequencyContainer megaFrequencyContainer = new MegaFrequencyContainer(megaConfig);

        int[] randomFrequencies = new Random().ints(70 / 5, 1, 5000).toArray();

        IntStream.range(1, 70 / 5 + 1)
                .parallel()
                .forEach(x ->
                    IntStream.range(0, randomFrequencies[x - 1])
                            .forEach(y -> megaFrequencyContainer
                                    .mainNumbersDrawn()
                                    .accept(
                                            Stream.iterate(
                                                    x * 5, counter -> counter > x * 5 - 5, counter -> counter - 1)
                                    ))
                )
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