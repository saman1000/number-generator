package results;

import games.GameConfig;
import history.GameFrequencyContainer;
import history.ResultsReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PrioriResultsConstructor {

    public static GameFrequencyContainer loadPriorResultsToFrequencyContainer(
            ResultsReader resultsReader,
            GameConfig gameConfig) throws IOException {
        return resultsReader.readLinesUsingScanner(
                Files.newBufferedReader(Paths.get(gameConfig.getFilePath())),
                gameConfig);

    }
}
