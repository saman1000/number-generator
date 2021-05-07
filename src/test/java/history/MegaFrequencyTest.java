package history;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NavigableMap;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class MegaFrequencyTest {

    @Test
    void getFrequencyOfNumber() {
        MegaFrequency megaFrequency = new MegaFrequency(10);
        Integer[] testNumbers = new Integer[10];
        IntStream.range(1, 11)
                .forEach(x -> {testNumbers[x-1]=x*10;});

        for (int counter = 1; counter <= 10; counter++) {
            for (int numberFrequency=testNumbers[counter-1]; numberFrequency > 0; numberFrequency--) {
                megaFrequency.numberOccurrenceObserved(counter);
            }
        }

        for (int counter=0; counter < 10; counter++) {
            assertEquals(testNumbers[counter]+1, megaFrequency.getFrequencyOfNumber(counter+1));
        }
    }
}