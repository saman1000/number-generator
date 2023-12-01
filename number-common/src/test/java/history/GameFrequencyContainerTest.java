package history;

import games.GameConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class GameFrequencyContainerTest {


    @Mock
    private GameConfig gameConfig;

    @BeforeEach
    public void initConfig() {
        Mockito.when(gameConfig.getMaxBallNumberValue()).thenReturn(25);
        Mockito.when(gameConfig.getMaxMainNumberValue()).thenReturn(70);
    }

    @Test
    void getBallNumberFrequency() {
        GameFrequencyContainer gameFrequencyContainer = new GameFrequencyContainer(
                gameConfig.getMaxMainNumberValue(),
                gameConfig.getMaxBallNumberValue()
        );

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(x ->
                    IntStream.iterate(0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                            .forEach(y -> gameFrequencyContainer.ballNumberDrawn().accept(x))
                )
        ;

        int[] savedFrequencies =
                gameFrequencyContainer.getFrequencyOfBallNumbers().getNumberFrequencies().entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .mapToInt(Map.Entry::getKey).toArray();
        Assertions.assertEquals(randomFrequencies[0]+1, savedFrequencies[0]);
        for (int counter = 1; counter < randomFrequencies.length; counter++) {
            Assertions.assertEquals(
                    randomFrequencies[counter]+1, savedFrequencies[counter]-savedFrequencies[counter-1]
            );
        }
    }

    @Test
    void getMainNumberFrequency() {
        GameFrequencyContainer gameFrequencyContainer = new GameFrequencyContainer(
                gameConfig.getMaxMainNumberValue(),
                gameConfig.getMaxBallNumberValue()
        );

        int[] randomFrequencies = new Random().ints(70 / 5, 1, 5000).toArray();

        IntStream.range(1, 70 / 5 + 1)
                .parallel()
                .forEach(x ->
                    IntStream.range(0, randomFrequencies[x - 1])
                            .forEach(y -> gameFrequencyContainer
                                    .mainNumbersDrawn()
                                    .accept(
                                            Stream.iterate(
                                                    x * 5, counter -> counter > x * 5 - 5, counter -> counter - 1)
                                                    .toList()
                                    ))
                )
        ;

        int[] savedFrequencies =
                gameFrequencyContainer.getFrequencyOfMainNumbers().getNumberFrequencies().entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .mapToInt(Map.Entry::getKey).toArray();
        Assertions.assertEquals(randomFrequencies[0]+1, savedFrequencies[0]);
        for (int counter = 0; counter < randomFrequencies.length; counter++) {
            for (int mainNumberCounter = counter == 0 ? 1 : counter*5; mainNumberCounter < counter*5+5; mainNumberCounter++) {
                Assertions.assertEquals(
                        randomFrequencies[counter]+1,
                        savedFrequencies[mainNumberCounter] - savedFrequencies[mainNumberCounter-1],
                        "counter=" + counter + " mainNumberCounter=" + mainNumberCounter);
            }
        }
    }
}