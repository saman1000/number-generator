package history;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
            chanceMap.put(oneEntry.getKey(), sum);
        }

        return chanceMap;
    }
}
