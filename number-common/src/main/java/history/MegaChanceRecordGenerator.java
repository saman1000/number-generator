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

    public synchronized Integer generateBallNumber(ChanceMethod chanceMethod) {
        NavigableMap<Integer, Integer> chanceMap = megaFrequencyContainer.getBallNumberChanceMap(chanceMethod);
        int total = chanceMap.lastKey();
        int chance = chanceGenerator.nextInt(total);
        return chanceMap.ceilingEntry(chance).getValue();
    }

    public synchronized List<Integer> generateMainNumbers(ChanceMethod chanceMethod) {
        NavigableMap<Integer, Integer> chanceMap = megaFrequencyContainer.getMainNumbersChanceMap(chanceMethod);
        int total = chanceMap.lastKey();
        List<Integer> generatedNumbers = new ArrayList<>();
        for (int counter = 0; counter < 5; counter++) {
            Integer oneNumber;
            do {
                oneNumber = chanceMap.ceilingEntry(chanceGenerator.nextInt(total)).getKey();
            } while (generatedNumbers.contains(oneNumber));
            generatedNumbers.add(oneNumber);
        }

        Collections.sort(generatedNumbers);
        return generatedNumbers;
    }

}
