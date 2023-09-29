package history;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * instances of this class generate main and ball numbers based on the frequency of former draws
 */
@Service("megaNumberGeneratorService")
public class GameChanceRecordGenerator implements IGameChanceRecordGenerator {

    private final GameFrequencyContainer gameFrequencyContainer;
    private final Random chanceGenerator;

    public GameChanceRecordGenerator(GameFrequencyContainer gameFrequencyContainer, Random megaRandomNumberGenerator) {
        this.gameFrequencyContainer = gameFrequencyContainer;
        this.chanceGenerator = megaRandomNumberGenerator;
    }

    @Override
    public synchronized Integer[] generateBallNumbers(ChanceMethod chanceMethod, int numberOfSets) {
        NavigableMap<Integer, Integer> chanceMap = gameFrequencyContainer.getBallNumberChanceMap(chanceMethod);
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

    @Override
    public synchronized List<Integer>[] generateMainNumbers(ChanceMethod chanceMethod, int numberOfSets) {
        List[] generatedSets = new List<?>[numberOfSets];

        NavigableMap<Integer, Integer> chanceMap = gameFrequencyContainer.getMainNumbersChanceMap(chanceMethod);
        int total = chanceMap.lastKey();

        for (int counter = 0; counter < numberOfSets; counter++) {
            generatedSets[counter] = generateOneMainNumberSet(chanceMap, total);
        }

        return generatedSets;
    }

}
