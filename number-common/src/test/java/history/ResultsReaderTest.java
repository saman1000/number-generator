package history;

import games.GameConfig;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultsReaderTest {

    @Test
    void testFrequencies() {
        GameFrequencyContainer gameFrequencyContainer = getGameFrequencyContainer();

        int[] savedBallNumberFrequencies =
                gameFrequencyContainer.getFrequencyOfBallNumbers().getNumberFrequencies().entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .mapToInt(Map.Entry::getKey).toArray();
        int[] savedMainNumberFrequencies =
                gameFrequencyContainer.getFrequencyOfMainNumbers().getNumberFrequencies().entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .mapToInt(Map.Entry::getKey).toArray();

        assertEquals(3, savedBallNumberFrequencies[14] - savedBallNumberFrequencies[13]);
        assertEquals(2, savedBallNumberFrequencies[24] - savedBallNumberFrequencies[23]);
        assertEquals(3, savedMainNumberFrequencies[3] - savedMainNumberFrequencies[2]);
        assertEquals(2, savedMainNumberFrequencies[18] - savedMainNumberFrequencies[17]);
        assertEquals(3, savedMainNumberFrequencies[24] - savedMainNumberFrequencies[23]);
        assertEquals(2, savedMainNumberFrequencies[30] - savedMainNumberFrequencies[29]);
        assertEquals(4, savedMainNumberFrequencies[36] - savedMainNumberFrequencies[35]);
        assertEquals(3, savedMainNumberFrequencies[45] - savedMainNumberFrequencies[44]);
        assertEquals(2, savedMainNumberFrequencies[54] - savedMainNumberFrequencies[53]);
        assertEquals(4, savedMainNumberFrequencies[66] - savedMainNumberFrequencies[65]);
    }

    private GameFrequencyContainer getGameFrequencyContainer() {
        GameConfig gameConfig = new GameConfig();
        gameConfig.setFilePath(".");
        gameConfig.setResultsPattern("(.*);(.*);(.*)");
        gameConfig.setMaxBallNumberValue(25);
        gameConfig.setMaxMainNumberValue(70);
        ResultsReader gameResultsReader = new ResultsReader();
        Readable lines = new StringReader("""
                Results for Mega Millions
                3/26/2021; 4,25,37,46,67; Mega Ball: 15
                12/11/2020; 19,31,37,55,67; Mega Ball: 25
                3/26/2021; 4,25,37,46,67; Mega Ball: 15
                All information is entered manually, and is subject to human error.
                Therefore, we can not guarantee the accuracy of this information.
                """);
        return gameResultsReader.readLinesUsingScanner(lines, gameConfig);
    }
}