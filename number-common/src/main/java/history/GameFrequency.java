package history;

import java.util.*;
import java.util.stream.Collectors;

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

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int counter = 0; counter < frequencies.length; counter++) {
            frequencyMap.put(counter + 1, frequencies[counter]);
        }
        Comparator<Map.Entry<Integer, Integer>> entryComparator = Map.Entry.comparingByValue(Comparator.naturalOrder());
        List<Map.Entry<Integer, Integer>> list = frequencyMap.entrySet().stream()
                .sorted(entryComparator)
                .collect(Collectors.toList());
        HashMap<Integer, Integer> swappedMap = new HashMap<>();
        while (list.size() >= 2) {
            Map.Entry<Integer, Integer> lowest = list.remove(0);
            Map.Entry<Integer, Integer> highest = list.remove(list.size() - 1);
            swappedMap.put(lowest.getKey(), highest.getValue());
            swappedMap.put(highest.getKey(), lowest.getValue());
            while (!list.isEmpty() && list.get(0).getValue().equals(lowest.getValue())) {
                swappedMap.put(list.remove(0).getKey(), highest.getValue());
            }
            while (!list.isEmpty() && list.get(list.size() - 1).getValue().equals(highest.getValue())) {
                swappedMap.put(list.remove(list.size() - 1).getKey(), lowest.getValue());
            }
        }
        if (!list.isEmpty()) {
            Map.Entry<Integer, Integer> last = list.remove(0);
            swappedMap.put(last.getKey(), last.getValue());
        }

        int sum = 0;
        for (Map.Entry<Integer, Integer> oneEntry: swappedMap.entrySet()) {
            sum += oneEntry.getValue();
            swappedFrequencyMap.put(sum, oneEntry.getKey());
        }

        return swappedFrequencyMap;
    }
}
