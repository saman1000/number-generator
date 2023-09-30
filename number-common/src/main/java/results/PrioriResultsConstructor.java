package results;

import history.ResultsReader;
import jakarta.annotation.PostConstruct;
import games.GameConfig;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("unused")
@Component
public class PrioriResultsConstructor {

    private final ResultsReader resultsReader;

    private final String filePath;

    private final String resultsPattern;

    public PrioriResultsConstructor(ResultsReader megResultsReader, GameConfig gameConfig) {
        this.resultsReader = megResultsReader;
        this.filePath = gameConfig.getFilePath();
        this.resultsPattern = gameConfig.getResultsPattern();
    }

    @PostConstruct
    public void fetchPriorResults() throws IOException {
        resultsReader.readLinesUsingScanner(Files.newBufferedReader(Paths.get(filePath)), resultsPattern);
    }
}
