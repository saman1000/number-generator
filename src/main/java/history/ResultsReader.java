package history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * Read all the valid lines
 * create frequency for each play number
 * create frequency for each ball number
 */
@Component("megaResultsReader")
public record ResultsReader(MegaFrequencyContainer megaFrequencyContainer) {

    private static final Logger logger = LoggerFactory.getLogger(ResultsReader.class);

    public void readLinesUsingScanner(Readable readable, String patternString) {
        try (Scanner scanner = new Scanner(readable)) {
            scanner
                    .findAll(patternString)
                    .forEach(this::updateFrequency)
            ;
        }
    }

    private void updateFrequency(MatchResult matchResult) {
        String[] groups = new String[matchResult.groupCount()];
        for (int counter = groups.length - 1; counter >= 0; counter--) {
            groups[counter] = matchResult.group(counter + 1).trim();
        }
        try {
            megaFrequencyContainer.mainNumbersDrawn().accept(MegaExtractor.getMainNumbers(groups[1]));
            megaFrequencyContainer.ballNumberDrawn().accept(
                    MegaExtractor.getBallNumber(groups[2])
                            .orElseThrow(
                                    () -> new IllegalStateException(String.format("No valid ball number: %s", groups[2])))
            );
        } catch (InvalidMegaNumberException ex) {
            logger.warn(String.format("failed to parse %s, %s because %s", groups[1], groups[2], ex.getMessage()));
        }
    }

    private PriorMegaMillionsResult addOneResult(MatchResult matchResult) {
        String[] groups = new String[matchResult.groupCount()];
        for (int counter = groups.length - 1; counter >= 0; counter--) {
            groups[counter] = matchResult.group(counter + 1).trim();
        }

        return MegaExtractor.extractResult(groups);
    }

}

