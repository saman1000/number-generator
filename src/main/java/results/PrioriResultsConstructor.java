package results;

import history.ResultsReader;
import mega.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class PrioriResultsConstructor {

    private ResultsReader resultsReader;

    private String filePath;

    private String resultsPattern;

    public PrioriResultsConstructor(ResultsReader megResultsReader, Config megaConfig) {
        this.resultsReader = megResultsReader;
        this.filePath = megaConfig.getFilePath();
        this.resultsPattern = megaConfig.getResultsPattern();
    }

    @PostConstruct
    public void fetchPriorResults() throws IOException {
        resultsReader.readLinesUsingScanner(Files.newBufferedReader(Paths.get(filePath)), resultsPattern);
    }
}
