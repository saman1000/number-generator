package history;

import java.util.*;
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

    public NavigableMap<Integer, Integer> getChanceMap() {
        NavigableMap<Integer, Integer> chanceMap = new TreeMap<>();

        for (int counter=0; counter < frequencies.length;) {
            chanceMap.put(frequencies[counter], ++counter);
        }

        return chanceMap;
    }
}
