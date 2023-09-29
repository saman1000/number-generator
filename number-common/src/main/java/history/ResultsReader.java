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
public record ResultsReader(GameFrequencyContainer gameFrequencyContainer) {

    private static final Logger logger = LoggerFactory.getLogger(ResultsReader.class);

    public void readLinesUsingScanner(Readable readable, String patternString) {
        try (Scanner scanner = new Scanner(readable)) {
            scanner
                    .findAll(patternString)
                    .forEach(this::updateFrequency)
            ;
        }
        logger.info(String.format("read %s records", gameFrequencyContainer.getNumberOfAcceptedRecords()));
    }

    private void updateFrequency(MatchResult matchResult) {
        String[] groups = new String[matchResult.groupCount()];
        for (int counter = groups.length - 1; counter >= 0; counter--) {
            groups[counter] = matchResult.group(counter + 1).trim();
        }
        try {
            gameFrequencyContainer.mainNumbersDrawn().accept(FrequencyExtractor.getMainNumbers(groups[1]));
            gameFrequencyContainer.ballNumberDrawn().accept(
                    FrequencyExtractor.getBallNumber(groups[2])
                            .orElseThrow(
                                    () -> new IllegalStateException(String.format("No valid ball number: %s", groups[2])))
            );
            gameFrequencyContainer.acceptedOneRecord();
        } catch (InvalidGameNumberException ex) {
            logger.warn(String.format("failed to parse %s, %s because %s", groups[1], groups[2], ex.getMessage()));
        }
    }

}

