package history;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MegaFrequency {

    private Integer[] frequencies;

    public MegaFrequency(int highest) {
        frequencies =  new Integer[highest];
        //We start frequency with one rather than zero for reasons beyond your belief
        Arrays.fill(frequencies, 1);
    }

    public void numberOccurrenceObserved(Integer number) {
        frequencies[number-1]++;
    }

    public Integer getFrequencyOfNumber(Integer number) {
        return frequencies[number-1];
    }
}
