package history;

import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * Read all the valid lines
 * create frequency for each play number
 * create frequency for each ball number
 */
@Component("megaResultsReader")
public class ResultsReader {

    private MegaFrequencyContainer megaFrequencyContainer;

    ResultsReader(MegaFrequencyContainer megaFrequencyContainer) {
        this.megaFrequencyContainer = megaFrequencyContainer;
    }

    public MegaFrequencyContainer readLinesUsingScanner(Readable readable, String patternString) {
        try (Scanner scanner = new Scanner(readable);) {
            scanner
                    .findAll(patternString)
                    .forEach(matchResult -> {
                        updateFrequency(matchResult);
                    })
            ;
        }
        return megaFrequencyContainer;
    }

    private void updateFrequency(MatchResult matchResult) {
        String[] groups = new String[matchResult.groupCount()];
        for (int counter = groups.length - 1; counter >= 0; counter--) {
            groups[counter] = matchResult.group(counter + 1).trim();
        }

        megaFrequencyContainer.mainNumbersDrawn().accept(MegaExtractor.getMainNumbers(groups[1]));
        megaFrequencyContainer.ballNumberDrawn().accept(
                MegaExtractor.getBallNumber(groups[2])
                        .orElseThrow(
                                () -> new IllegalStateException(String.format("No valid ball number: %s", groups[2])))
        );
    }

    private PriorMegaMillionsResult addOneResult(MatchResult matchResult) {
        String[] groups = new String[matchResult.groupCount()];
        for (int counter = groups.length - 1; counter >= 0; counter--) {
            groups[counter] = matchResult.group(counter + 1).trim();
        }

        return MegaExtractor.extractResult(groups);
    }

}

