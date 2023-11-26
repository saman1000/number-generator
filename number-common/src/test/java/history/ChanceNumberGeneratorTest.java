package history;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ChanceNumberGeneratorTest {

    @Test
    public void testNumberAndPairChanceAreUsed() {
        GameFrequencyContainer gameFrequencyContainer = new GameFrequencyContainer(10, 5);
        gameFrequencyContainer.mainNumbersDrawn().accept(Arrays.asList(1, 2, 3, 4, 5));
        gameFrequencyContainer.mainNumbersDrawn().accept(Arrays.asList(1, 3, 4, 5, 6));
        gameFrequencyContainer.mainNumbersDrawn().accept(Arrays.asList(1, 6, 7, 8, 9));

        ChanceNumberGenerator chanceNumberGenerator = new ChanceNumberGenerator(null, null);
        chanceNumberGenerator.generateNumber();

    }
}
