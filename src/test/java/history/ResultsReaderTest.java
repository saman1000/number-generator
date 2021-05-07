package history;

import org.junit.jupiter.api.Test;
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

    @Test
    public void testFrequencies() {
        Readable lines = new StringReader("Results for Mega Millions\n" +
                "3/26/2021; 4,25,37,46,67; Mega Ball: 15\n" +
                "12/11/2020; 19,31,37,55,67; Mega Ball: 25\n" +
                "3/26/2021; 4,25,37,46,67; Mega Ball: 15\n"+
                "All information is entered manually, and is subject to human error. \n" +
                "Therefore, we can not guarantee the accuracy of this information.");
        MegaFrequencyContainer megaFrequencyContainer = ResultsReader.readLinesUsingScanner(lines, "(.*);(.*);(.*)");
        megaFrequencyContainer.prepare();
        assertEquals(3, megaFrequencyContainer.getBallNumberFrequency(15));
        assertEquals(2, megaFrequencyContainer.getBallNumberFrequency(25));
        assertEquals(3, megaFrequencyContainer.getMainNumberFrequency(4));
        assertEquals(2, megaFrequencyContainer.getMainNumberFrequency(19));
        assertEquals(3, megaFrequencyContainer.getMainNumberFrequency(25));
        assertEquals(2, megaFrequencyContainer.getMainNumberFrequency(31));
        assertEquals(4, megaFrequencyContainer.getMainNumberFrequency(37));
        assertEquals(3, megaFrequencyContainer.getMainNumberFrequency(46));
        assertEquals(2, megaFrequencyContainer.getMainNumberFrequency(55));
        assertEquals(4, megaFrequencyContainer.getMainNumberFrequency(67));
    }
}