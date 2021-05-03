package history;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MegaFrequency {

    private Integer[] frequencies;

    public MegaFrequency(int highest) {
        frequencies = new Integer[highest];
        //We start frequency with one rather than zero for reasons beyond your belief
        Arrays.fill(frequencies, 1);
    }

    public void numberOccurrenceObserved(Integer number) {
        frequencies[number - 1]++;
    }

    public Integer getFrequencyOfNumber(Integer number) {
        return frequencies[number - 1];
    }

    public List<Integer> getChanceList() {
        return Stream.of(frequencies)
                .sorted()
                .collect(Collectors.toUnmodifiableList())
                ;
    }
}
