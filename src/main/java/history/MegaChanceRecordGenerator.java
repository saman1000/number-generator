package history;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;

/**
 * instances of this class generate main and ball numbers based on the frequency of former draws
 */
public class MegaChanceRecordGenerator {

    private MegaFrequencyContainer megaFrequencyContainer;
    Random chanceGenerator;

    public MegaChanceRecordGenerator(MegaFrequencyContainer megaFrequencyContainer) {
        this.megaFrequencyContainer = megaFrequencyContainer;
        chanceGenerator = new Random();
    }

    public Integer generateBallNumber() {
        NavigableMap<Integer, Integer> chanceMap = megaFrequencyContainer.getBallNumberChanceMap();
        int total = chanceMap.lastKey();
        int chance = chanceGenerator.nextInt(total);
        return chanceMap.ceilingEntry(chance).getValue();
    }

    public List<Integer> generateMainNumbers() {
        NavigableMap<Integer, Integer> chanceMap = megaFrequencyContainer.getMainNumbersChanceMap();
        int total = chanceMap.lastKey();
        List<Integer> generatedNumbers = new ArrayList<>();
        for (int counter = 0; counter < 5; counter++) {
            Integer oneNumber = null;
            do {
                oneNumber = chanceMap.ceilingEntry(chanceGenerator.nextInt(total)).getValue();
            } while (generatedNumbers.contains(oneNumber));
            generatedNumbers.add(oneNumber);
        }

        return generatedNumbers;
    }

}
