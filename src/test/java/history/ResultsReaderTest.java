package history;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ResultsReaderTest {

    @Test
    public void testReadUsingScanner() {
        String lines = "Results for Mega Millions\n" +
                "3/26/2021; 4,25,37,46,67; Mega Ball: 15\n" +
                "12/11/2020; 19,31,37,55,67; Mega Ball: 25\n" +
                "\n" +
                "All information is entered manually, and is subject to human error. \n" +
                "Therefore, we can not guarantee the accuracy of this information.";
        List<String[]> actualMatchedGroups = ResultsReader.readUsingScanner(new StringReader(lines), "(.*);(.*);(.*)");
        assertTrue(actualMatchedGroups.size() == 2);
        assertAll(
                () -> assertEquals("3/26/2021", actualMatchedGroups.get(0)[0]),
                () -> assertEquals("4,25,37,46,67", actualMatchedGroups.get(0)[1]),
                () -> assertEquals("Mega Ball: 15", actualMatchedGroups.get(0)[2])
        );
    }

}