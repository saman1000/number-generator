package history;

import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MegaExtractor {
    private static Pattern numberPattern = Pattern.compile("(\\d+)");

    public PriorMegaMillionsResult extractResult(String[] oneResultStr) {
        List<Integer> numbers = getMainNumbers(oneResultStr[1]).collect(Collectors.toList());
        Optional<Integer> ballNumber = getBallNumber(oneResultStr[2]);

        return new PriorMegaMillionsResult(
                oneResultStr[0],
                numbers,
                ballNumber.orElseThrow(
                        () -> new IllegalStateException(String.format("No valid ball number: %s", oneResultStr[2]))
                )
        );
    }

    public static Optional<Integer> getBallNumber(String ballNumberString) {
        return numberPattern.matcher(ballNumberString)
                    .results()
                    .map(MatchResult::group)
                    .map(Integer::parseUnsignedInt)
                    .findFirst();
    }

    public static Stream<Integer> getMainNumbers(String mainNumbersString) {
        return numberPattern.matcher(mainNumbersString)
                .results()
                .map(MatchResult::group)
                .map(Integer::parseUnsignedInt)
                .sorted()
                .distinct()
                ;
    }

}
