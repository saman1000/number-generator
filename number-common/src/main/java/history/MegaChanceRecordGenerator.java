package history;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * instances of this class generate main and ball numbers based on the frequency of former draws
 */
@Service("megaNumberGeneratorService")
public class MegaChanceRecordGenerator {

    private final MegaFrequencyContainer megaFrequencyContainer;
    private final Random chanceGenerator;

    public MegaChanceRecordGenerator(MegaFrequencyContainer megaFrequencyContainer, Random megaRandomNumberGenerator) {
        this.megaFrequencyContainer = megaFrequencyContainer;
        this.chanceGenerator = megaRandomNumberGenerator;
    }

    public synchronized Integer[] generateBallNumber(ChanceMethod chanceMethod, int numberOfSets) {
        NavigableMap<Integer, Integer> chanceMap = megaFrequencyContainer.getBallNumberChanceMap(chanceMethod);
        int total = chanceMap.lastKey();
        Integer[] ballNumbers = new Integer[numberOfSets];
        for (int counter = 0; counter < numberOfSets; counter++) {
            ballNumbers[counter] = chanceMap.ceilingEntry(chanceGenerator.nextInt(total)).getValue();
        }

        return ballNumbers;
    }

    private List<Integer> generateOneMainNumberSet(
            NavigableMap<Integer, Integer> chanceMap,
            int total) {
        List<Integer> generatedNumbers = new ArrayList<>(5);
        for (int counter = 0; counter < 5; counter++) {
            Integer oneNumber;
            do {
                int chance = chanceGenerator.nextInt(total);
                oneNumber = chanceMap.ceilingEntry(chance).getValue();
            } while (generatedNumbers.contains(oneNumber));
            generatedNumbers.add(oneNumber);
        }

        Collections.sort(generatedNumbers);
        return generatedNumbers;
    }

    public synchronized List<Integer>[] generateMainNumbers(ChanceMethod chanceMethod, int numberOfSets) {
        List<Integer>[] generatedSets = new List[numberOfSets];

        NavigableMap<Integer, Integer> chanceMap = megaFrequencyContainer.getMainNumbersChanceMap(chanceMethod);
        int total = chanceMap.lastKey();

        for (int counter = 0; counter < numberOfSets; counter++) {
            generatedSets[counter] = generateOneMainNumberSet(chanceMap, total);
        }

        return generatedSets;
    }

}
