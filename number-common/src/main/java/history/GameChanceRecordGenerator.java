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

    private final Map<String, ChanceNumberGenerator> chanceNumberGeneratorMap;

    private final ResultsReader resultsReader;

    private final AllConfigs allGames;

    public GameChanceRecordGenerator(AllConfigs allGames, ResultsReader gameResultsReader) {
        chanceNumberGeneratorMap = new HashMap<>();
        resultsReader = gameResultsReader;
        allGames.getGames().entrySet()
                .stream()
                .parallel()
                .forEach(
                        oneEntry -> {
                            GameFrequencyContainer gameFrequencyContainer =
                                    loadOneFrequencyContainer(oneEntry.getValue());
                            chanceNumberGeneratorMap.put(
                                    oneEntry.getKey(),
                                    new ChanceNumberGenerator(
                                       gameFrequencyContainer.getFrequencyOfBallNumbers(),
                                       gameFrequencyContainer.getFrequencyOfMainNumbers(),
                                       gameFrequencyContainer.getPairingFrequency()
                                    ));
                        }
                );
        this.allGames = allGames;
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

        ChanceNumberGenerator chanceNumberGenerator = chanceNumberGeneratorMap.get(gameName);
        Integer[] ballNumbers = new Integer[numberOfSets];
        for (int counter = 0; counter < numberOfSets; counter++) {
            ballNumbers[counter] = chanceNumberGenerator.generateBallNumber(chanceMethod);
        }

        return ballNumbers;
    }

    public synchronized List<List<Integer>> generateMainNumbers(String gameName, ChanceMethod chanceMethod, int numberOfSets) {
        List<List<Integer>> generatedSets = new ArrayList<>();

        ChanceNumberGenerator chanceNumberGenerator = chanceNumberGeneratorMap.get(gameName);
        for (int counter = 0; counter < numberOfSets; counter++) {
            generatedSets.add(
                    chanceNumberGenerator.generateMainNumberSet(
                            chanceMethod, allGames.getGames().get(gameName).getMainNumberSetSize())
            );
        }

        return generatedSets;
    }
}
