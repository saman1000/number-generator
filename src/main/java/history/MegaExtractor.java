package history;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MegaExtractor {
    private Pattern numberPattern = Pattern.compile(",");
    private Pattern ballNumberPattern = Pattern.compile("\\d+");

    public void extractResult(String[] oneResultStr) {
//        3/19/2021; 9,14,40,58,69; Mega Ball: 8
        Stream<Integer> numbers = numberPattern.splitAsStream(oneResultStr[1])
                .map(Integer::parseInt);
        Integer ballNumber = Integer.parseUnsignedInt(ballNumberPattern.matcher(oneResultStr[2]).group());
//        PriorMegaMillionsResult = new PriorMegaMillionsResult(oneResultStr[0], )
    }

}
