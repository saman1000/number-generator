package history;

import games.AllConfigs;
import games.GameConfig;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class GameChanceRecordGeneratorTest {

    @Mock
    private AllConfigs allGames;

    @BeforeEach
    public void initConfig() {
        GameConfig megaConfig = Mockito.mock(GameConfig.class);
        Mockito.when(megaConfig.getMaxBallNumberValue()).thenReturn(25);
        Mockito.when(megaConfig.getMaxMainNumberValue()).thenReturn(70);
        Mockito.when(megaConfig.getFilePath()).thenReturn(".");
        Mockito.when(allGames.getGames()).thenReturn(Collections.singletonMap("mega", megaConfig));
    }

    @Test
    void shouldGenerateTenBallNumbers() {
        GameFrequencyContainer gameFrequencyContainer = new GameFrequencyContainer(
                allGames.getGames().get("mega").getMaxMainNumberValue(),
                allGames.getGames().get("mega").getMaxBallNumberValue()
        );

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(
                        x -> IntStream.iterate(
                                        0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                                .forEach(y -> gameFrequencyContainer.ballNumberDrawn().accept(x))
                )
        ;

        ResultsReader mockedResultsReader = Mockito.mock(ResultsReader.class);
        Mockito.when(
                mockedResultsReader.readLinesUsingScanner(
                        Mockito.any(), Mockito.any())).thenReturn(gameFrequencyContainer
        );

        IGameChanceRecordGenerator megaChanceRecordGenerator =
                new GameChanceRecordGenerator(allGames, mockedResultsReader);

        Integer[] generatedBallNumberSet =
                megaChanceRecordGenerator.generateBallNumbers("mega", ChanceMethod.STRAIGHT, 10);
        Arrays.stream(generatedBallNumberSet)
                        .forEach(
                                ballNumber -> assertTrue(ballNumber > 0 && ballNumber < 26)
                        );
    }

    @Test
    void shouldGenerateExpectedVarianceForBallNumbers() {
        GameFrequencyContainer gameFrequencyContainer = new GameFrequencyContainer(
                allGames.getGames().get("mega").getMaxMainNumberValue(),
                allGames.getGames().get("mega").getMaxBallNumberValue()
        );

        int[] randomFrequencies = new Random().ints(25, 1, 500).toArray();
        IntStream.range(1, 26)
                .parallel()
                .forEach(x ->
                    IntStream.iterate(0, counter -> counter < randomFrequencies[x - 1], counter -> counter + 1)
                            .forEach(y -> gameFrequencyContainer.ballNumberDrawn().accept(x))
                )
        ;

        ResultsReader mockedResultsReader = Mockito.mock(ResultsReader.class);
        Mockito.when(
                mockedResultsReader.readLinesUsingScanner(
                        Mockito.any(), Mockito.any())).thenReturn(gameFrequencyContainer
        );

        IGameChanceRecordGenerator megaChanceRecordGenerator =
                new GameChanceRecordGenerator(allGames, mockedResultsReader);

        Integer[] ballNumbers = megaChanceRecordGenerator.generateBallNumbers("mega", ChanceMethod.STRAIGHT,
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

}