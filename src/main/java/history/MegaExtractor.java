package history;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MegaExtractor {
    private Pattern numberPattern = Pattern.compile("(\\d+)");

    public PriorMegaMillionsResult extractResult(String[] oneResultStr) {
        List<Integer> numbers = numberPattern.matcher(oneResultStr[1])
                .results()
                .map(MatchResult::group)
                .map(Integer::parseUnsignedInt)
                .sorted()
                .distinct()
                .collect(Collectors.toList())
                ;
        Optional<Integer> ballNumber = numberPattern.matcher(oneResultStr[2])
                .results()
                .map(MatchResult::group)
                .map(Integer::parseUnsignedInt)
                .findFirst()
                ;

        return new PriorMegaMillionsResult(
                oneResultStr[0],
                numbers,
                ballNumber.orElseThrow(IllegalStateException::new)
        );
    }

}
