package history;

import games.AllConfigs;
import games.GameConfig;
import org.springframework.stereotype.Service;
import results.PrioriResultsConstructor;

import java.io.IOException;
import java.util.*;

/**
 * instances of this class generate main and ball numbers based on the frequency of former draws
 */
@Service("gamesNumberGeneratorService")
public class GameChanceRecordGenerator implements IGameChanceRecordGenerator {

    private final Map<String, GameFrequencyContainer> frequencyContainerMap;

    private final ResultsReader resultsReader;

    private final Random chanceGenerator;

    public GameChanceRecordGenerator(AllConfigs allGames, ResultsReader gameResultsReader) {
        frequencyContainerMap = new HashMap<>();
        resultsReader = gameResultsReader;
        allGames.getGames().entrySet()
                .stream()
                .parallel()
                .forEach(
                        oneEntry -> frequencyContainerMap.put(
                                oneEntry.getKey(), loadOneFrequencyContainer(oneEntry.getValue())
                        )
                );

        chanceGenerator = new Random();
    }

    private GameFrequencyContainer loadOneFrequencyContainer(GameConfig gameConfig) throws FailedToReadPastResultsException {
        try {
            return PrioriResultsConstructor.loadPriorResultsToFrequencyContainer(
                    resultsReader,
                    gameConfig
            );
        } catch (IOException e) {
            throw new FailedToReadPastResultsException(gameConfig.getFilePath());
        }
    }

    public synchronized Integer[] generateBallNumbers(String gameName, ChanceMethod chanceMethod, int numberOfSets) {

        NavigableMap<Integer, Integer> chanceMap = frequencyContainerMap.get(gameName).getBallNumberChanceMap(chanceMethod);
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
        Integer oneNumber;
        while (generatedNumbers.size() < 5) {
            oneNumber = chanceMap.ceilingEntry(chanceGenerator.nextInt(total)).getValue();
            if( !generatedNumbers.contains(oneNumber) ) {
                generatedNumbers.add(oneNumber);
            }
        }

        Collections.sort(generatedNumbers);
        return generatedNumbers;
    }

    public synchronized List<List<Integer>> generateMainNumbers(String gameName, ChanceMethod chanceMethod, int numberOfSets) {
        List<List<Integer>> generatedSets = new ArrayList<>();

        NavigableMap<Integer, Integer> chanceMap = frequencyContainerMap.get(gameName).getMainNumbersChanceMap(chanceMethod);
        int total = chanceMap.lastKey();

        for (int counter = 0; counter < numberOfSets; counter++) {
            generatedSets.add(generateOneMainNumberSet(chanceMap, total));
        }

        return generatedSets;
    }
}
