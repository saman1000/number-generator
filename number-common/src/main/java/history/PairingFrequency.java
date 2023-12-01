package history;

import java.util.List;
import java.util.stream.IntStream;

public class PairingFrequency {

    /**
     * value at index n represents frequency of mainNumber and n+1
     * for example if mainNumber is 9, and value at index 20 is 80, then
     * we know 9 has appeared 80 times with 21. Also value at index 8
     * is always zero because 9 does not appear with itself
     */
    private final int[][] pairingArrays;

    public PairingFrequency(int maxMainNumberValue) {
        this.pairingArrays = new int[maxMainNumberValue][maxMainNumberValue];
    }

    public void parseMainNumbers(List<Integer> sortedNumberArray) {
        sortedNumberArray.forEach(oneMainNumber -> {
            for (int oneNumber : sortedNumberArray) {
                pairingArrays[oneMainNumber - 1][oneNumber - 1] += oneMainNumber == oneNumber ? 0 : 1;
            }
        });
    }

    public Integer[] getPairingFrequencies(int mainNumber) {
        return IntStream.of(pairingArrays[mainNumber - 1]).boxed().toArray(Integer[]::new);
    }

}
