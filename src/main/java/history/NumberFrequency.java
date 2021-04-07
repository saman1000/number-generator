package history;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.TreeSet;

public class NumberFrequency implements Serializable {

    public static final long serialVersionUID = 1L;

    private ArrayList<OneFrequency> m_frequency;

    private ArrayList<Integer> fullList;

    public NumberFrequency() {
        initialize();
    }

    private void initialize() {
        m_frequency = new ArrayList<OneFrequency>();

        // add the lottery numbers
        for (int counter = 1; counter <= 49; counter++) {
            m_frequency.add(new OneFrequency(counter));
        }

        fullList = new ArrayList<Integer>();
    }

    public void determineFrequencies(Collection<Integer> numbers) {
        for (OneFrequency oneFreq : m_frequency) {
            oneFreq.updateFrequency(numbers);
        }

        addNumbersToList();
    }

    private void addNumbersToList() {
        for (OneFrequency oneFreq : m_frequency) {
            oneFreq.positionNumber(fullList);
        }
    }

    public Integer[] generateOneSet() {
        Integer[] oneSet = new Integer[6];

        TreeSet<Integer> selectedNumbers = new TreeSet<Integer>();
        Random random = new Random();
        int onePosition = 0;
        int maxPosition = fullList.size() - 1;
        Integer oneNumber = null;
        for (int counter = 0; counter < 6; counter++) {
            do {
                onePosition = random.nextInt(maxPosition);
                oneNumber = fullList.get(onePosition);
            } while (!selectedNumbers.add(oneNumber));
            oneSet[counter] = oneNumber;
        }

        return oneSet;
    }
}
