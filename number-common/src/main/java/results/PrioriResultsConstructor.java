package results;

import games.GameConfig;
import history.GameFrequencyContainer;
import history.ResultsReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("unused")
//@Component
public class PrioriResultsConstructor {

//    private final ResultsReader resultsReader;
//
//    private final String filePath;
//
//    private final String resultsPattern;
//
//    public PrioriResultsConstructor(ResultsReader megResultsReader, GameConfig gameConfig) {
//        this.resultsReader = megResultsReader;
//        this.filePath = gameConfig.getFilePath();
//        this.resultsPattern = gameConfig.getResultsPattern();
//    }
//
////    @PostConstruct
//    public void fetchPriorResults(GameFrequencyContainer gameFrequencyContainer) throws IOException {
//        resultsReader.readLinesUsingScanner(gameFrequencyContainer, Files.newBufferedReader(Paths.get(filePath)), resultsPattern);
//    }

    public static GameFrequencyContainer loadPriorResultsToFrequencyContainer(
            ResultsReader resultsReader,
            GameConfig gameConfig) throws IOException {
        return resultsReader.readLinesUsingScanner(
                Files.newBufferedReader(Paths.get(gameConfig.getFilePath())),
                gameConfig);

    }
}
