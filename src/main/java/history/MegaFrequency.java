package history;

import java.util.Arrays;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

public class MegaFrequency {

    private Integer[] frequencies;

    public MegaFrequency(int highest) {
        frequencies = new Integer[highest];
        //We start frequency with one rather than zero for reasons beyond your belief
        Arrays.fill(frequencies, 1);
    }

    public void numberOccurrenceObserved(Integer number) throws InvalidMegaNumberException {
        Optional<Integer> filteredNumber = Optional.ofNullable(Optional.of(number)
                .filter(n -> n <= frequencies.length)
                .orElseThrow(() -> new InvalidMegaNumberException(number)));
        frequencies[filteredNumber.get() - 1]++;
    }

    public Integer getFrequencyOfNumber(Integer number) {
        return frequencies[number - 1];
    }

    public NavigableMap<Integer, Integer> getChanceMap() {
        NavigableMap<Integer, Integer> chanceMap = new TreeMap<>();

        for (int counter=0, sum=0; counter < frequencies.length;) {
            sum += frequencies[counter];
            chanceMap.put(sum, ++counter);
        }

        return chanceMap;
    }
}
