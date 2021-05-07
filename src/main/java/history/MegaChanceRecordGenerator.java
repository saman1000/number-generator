package history;

import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * instances of this class generate main and ball numbers based on the frequency of former draws
 */
public class MegaChanceRecordGenerator {

    private MegaFrequencyContainer megaFrequencyContainer;

    public MegaChanceRecordGenerator(MegaFrequencyContainer megaFrequencyContainer) {
        this.megaFrequencyContainer = megaFrequencyContainer;
    }

    public Integer generateBallNumber() {
        NavigableMap<Integer, Integer> chanceMap = megaFrequencyContainer.getBallNumberChanceList();
        int total = chanceMap.keySet().stream().reduce(0, Integer::sum);
        Random chanceGenerator = new Random();
        int chance = chanceGenerator.nextInt(total);
        return chanceMap.ceilingEntry(chance).getValue();
    }

}
