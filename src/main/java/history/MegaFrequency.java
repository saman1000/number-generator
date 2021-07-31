package history;

import org.springframework.util.LinkedMultiValueMap;

import java.util.*;

public class MegaFrequency {

    private final Integer[] frequencies;

    public MegaFrequency(int highest) {
        frequencies = new Integer[highest];
        //We start frequency with one rather than zero for reasons beyond your belief
        Arrays.fill(frequencies, 1);
    }

    public void numberOccurrenceObserved(Integer number) throws InvalidMegaNumberException {
        if (number > frequencies.length) {
            throw new InvalidMegaNumberException(number);
        }

        frequencies[number-1]++;
    }

    public Integer getFrequencyOfNumber(Integer number) {
        return frequencies[number - 1];
    }

    public NavigableMap<Integer, Integer> getChanceMap() {
        NavigableMap<Integer, Integer> chanceMap = new TreeMap<>();

        for (int counter = 0, sum = 0; counter < frequencies.length; ) {
            sum += frequencies[counter];
            chanceMap.put(sum, ++counter);
        }

        return chanceMap;
    }

    public NavigableMap<Integer, Integer> getSwappedChanceMap() {
        NavigableMap<Integer, Integer> chanceMap = new TreeMap<>();

        LinkedMultiValueMap<Integer, Integer> multiValueMap = new LinkedMultiValueMap<>();

        for (int counter = 0; counter < frequencies.length; ) {
            multiValueMap.add(frequencies[counter], ++counter);
        }

        TreeMap<Integer, Collection<Integer>> treeMap = new TreeMap<>(multiValueMap);

        LinkedList<AbstractMap.SimpleEntry<Integer, Collection<Integer>>> listOfEntries =  new LinkedList<>();
        while (!treeMap.isEmpty()) {
            swapAndAddEntries(
                    listOfEntries,
                    treeMap.pollFirstEntry(),
                    treeMap.pollLastEntry());
        }

        listOfEntries.forEach(entry ->
            entry.getValue().forEach(
                    number ->
                            chanceMap.put(entry.getKey() + (chanceMap.isEmpty() ? 0 : chanceMap.lastKey()), number))
        );

        return chanceMap;
    }

    private void swapAndAddEntries(LinkedList<AbstractMap.SimpleEntry<Integer, Collection<Integer>>> listOfEntries,
                            Map.Entry<Integer, Collection<Integer>> left,
                            Map.Entry<Integer, Collection<Integer>> right) {
        if (left == null)
            return;

        if (right == null) {
            listOfEntries.add(new AbstractMap.SimpleEntry<>(left.getKey(), left.getValue()));
        } else {
            listOfEntries.add(new AbstractMap.SimpleEntry<>(left.getKey(), right.getValue()));
            listOfEntries.add(new AbstractMap.SimpleEntry<>(right.getKey(), left.getValue()));
        }
    }
}
