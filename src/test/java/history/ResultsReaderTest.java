package history;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        value="integration test",
        classes = {ResultsReader.class, MegaFrequencyContainer.class, MegaExtractor.class}
        )
class ResultsReaderTest {

    @Test
    public void testFrequencies(@Qualifier("megaResultsReader") ResultsReader megaResultsReader, @Qualifier("megaFrequencyContainer") MegaFrequencyContainer megaFrequencyContainer) {
        Readable lines = new StringReader("Results for Mega Millions\n" +
                "3/26/2021; 4,25,37,46,67; Mega Ball: 15\n" +
                "12/11/2020; 19,31,37,55,67; Mega Ball: 25\n" +
                "3/26/2021; 4,25,37,46,67; Mega Ball: 15\n"+
                "All information is entered manually, and is subject to human error. \n" +
                "Therefore, we can not guarantee the accuracy of this information.");
        megaResultsReader.readLinesUsingScanner(lines, "(.*);(.*);(.*)");

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