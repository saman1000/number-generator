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

        // 1. Group indices by frequency (sorted by frequency ascending)
        SortedMap<Integer, List<Integer>> sortedFrequencyMap = new TreeMap<>();
        for (int counter = 0; counter < frequencies.length; counter++) {
            sortedFrequencyMap.computeIfAbsent(frequencies[counter], k -> new ArrayList<>()).add(counter + 1);
        }

        // 2. Get a list of frequencies in DESCENDING order
        List<Integer> descendingFrequencies = sortedFrequencyMap.keySet().stream()
                .sorted(Collections.reverseOrder())
                .toList();

        // 3. Pair the highest frequencies with the numbers that had the lowest frequencies
        int sum = 0;
        int freqIndex = 0;

        for (List<Integer> numbers : sortedFrequencyMap.values()) {
            int currentHighFreq = descendingFrequencies.get(freqIndex++);
            for (Integer number : numbers) {
                sum += currentHighFreq;
                swappedFrequencyMap.put(sum, number);
            }
        }

        return swappedFrequencyMap;
    }
}
