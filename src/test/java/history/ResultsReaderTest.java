package history;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ResultsReaderTest {

    private static Stream<Arguments> resultLines() {
        return Stream.of(
                Arguments.of(new StringReader("Results for Mega Millions\n" +
                        "3/26/2021; 4,25,37,46,67; Mega Ball: 15\n" +
                        "12/11/2020; 19,31,37,55,67; Mega Ball: 25\n" +
                        "\n" +
                        "All information is entered manually, and is subject to human error. \n" +
                        "Therefore, we can not guarantee the accuracy of this information."), 2),
                Arguments.of(new StringReader("Results for Mega Millions\n" +
                        "3/26/2021; 4,25,37,46,67; Mega Ball: 15\n" +
                        "\n" +
                        "All information is entered manually, and is subject to human error. \n" +
                        "Therefore, we can not guarantee the accuracy of this information."), 1),
                Arguments.of(new StringReader("Results for Mega Millions\n" +
                        "\n" +
                        "All information is entered manually, and is subject to human error. \n" +
                        "Therefore, we can not guarantee the accuracy of this information."), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("resultLines")
    public void testReadUsingScanner(Readable lines, int expectedSize) {
        List<String[]> actualMatchedGroups = ResultsReader.readLinesUsingScanner(lines, "(.*);(.*);(.*)");
        assertEquals(expectedSize, actualMatchedGroups.size());
    }
}