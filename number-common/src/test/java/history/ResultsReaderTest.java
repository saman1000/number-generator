package history;

import games.MegaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        value="integration test",
        classes = {ResultsReader.class, GameFrequencyContainer.class, FrequencyExtractor.class, MegaConfig.class}
        )
class ResultsReaderTest {

    @Test
    void testFrequencies(@Qualifier("megaResultsReader") ResultsReader megaResultsReader, @Qualifier("gameFrequencyContainer") GameFrequencyContainer gameFrequencyContainer) {
        Readable lines = new StringReader("""
                Results for Mega Millions
                3/26/2021; 4,25,37,46,67; Mega Ball: 15
                12/11/2020; 19,31,37,55,67; Mega Ball: 25
                3/26/2021; 4,25,37,46,67; Mega Ball: 15
                All information is entered manually, and is subject to human error.
                Therefore, we can not guarantee the accuracy of this information.
                """);
        megaResultsReader.readLinesUsingScanner(lines, "(.*);(.*);(.*)");

        assertEquals(3, gameFrequencyContainer.getBallNumberFrequency(15));
        assertEquals(2, gameFrequencyContainer.getBallNumberFrequency(25));
        assertEquals(3, gameFrequencyContainer.getMainNumberFrequency(4));
        assertEquals(2, gameFrequencyContainer.getMainNumberFrequency(19));
        assertEquals(3, gameFrequencyContainer.getMainNumberFrequency(25));
        assertEquals(2, gameFrequencyContainer.getMainNumberFrequency(31));
        assertEquals(4, gameFrequencyContainer.getMainNumberFrequency(37));
        assertEquals(3, gameFrequencyContainer.getMainNumberFrequency(46));
        assertEquals(2, gameFrequencyContainer.getMainNumberFrequency(55));
        assertEquals(4, gameFrequencyContainer.getMainNumberFrequency(67));
    }
}