package history;

import java.util.*;

public class GameFrequency {

    private final Integer[] frequencies;

    private NavigableMap<Integer, Integer> cachedChanceMap;
    private NavigableMap<Integer, Integer> cachedSwappedChanceMap;

    public GameFrequency(int highest) {
        frequencies = new Integer[highest];
        //We start frequency with one rather than zero for reasons beyond your belief
        Arrays.fill(frequencies, 1);
    }

    public void numberOccurrenceObserved(Integer number) throws InvalidGameNumberException {
        if (number > frequencies.length) {
            throw new InvalidGameNumberException(number);
        }

        frequencies[number-1]++;
    }

    public synchronized NavigableMap<Integer, Integer> getNumberFrequencies() {
        return Optional.ofNullable(cachedChanceMap).orElseGet(() -> {
            cachedChanceMap = generateFrequencyMap(frequencies);
            return cachedChanceMap;
        });
    }

    static NavigableMap<Integer, Integer> generateFrequencyMap(Integer[] frequencies) {
        NavigableMap<Integer, Integer> frequencyMap = new TreeMap<>();

        for (int counter = 0, sum = 0; counter < frequencies.length; ) {
            sum += frequencies[counter];
            frequencyMap.put(sum, ++counter);
        }

        return frequencyMap;
    }

    public NavigableMap<Integer, Integer> getSwappedNumberFrequencies() {
        return Optional.ofNullable(cachedSwappedChanceMap).orElseGet(() -> {
            cachedSwappedChanceMap = generateSwappedFrequencyMap(frequencies);
            return cachedSwappedChanceMap;
        });
    }

    static NavigableMap<Integer, Integer> generateSwappedFrequencyMap(Integer[] frequencies) {
        NavigableMap<Integer, Integer> swappedFrequencyMap = new TreeMap<>();

        SortedMap<Integer, List<Integer>> sortedFrequency = new TreeMap<>();
        for (int counter = 0; counter < frequencies.length; counter++) {
            sortedFrequency.computeIfAbsent(frequencies[counter], k -> new ArrayList<>()).add(counter + 1);
        }

        int sum = 0;
        Integer[] numberFrequencies = sortedFrequency.keySet().toArray(Integer[]::new);
        @SuppressWarnings("unchecked")
        List<Integer>[] numberList = sortedFrequency.values().toArray(new List[0]);
        for (int counter = numberFrequencies.length - 1, reverseCounter = 0; counter >= 0; counter--, reverseCounter++) {
            for(Integer aNumber : numberList[reverseCounter]) {
                sum += numberFrequencies[counter];
                swappedFrequencyMap.put(sum, aNumber);
            }
        }

        return swappedFrequencyMap;
    }
}
