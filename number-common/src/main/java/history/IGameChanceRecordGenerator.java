package history;

import java.util.List;

public interface IGameChanceRecordGenerator {
    Integer[] generateBallNumbers(String gameName, ChanceMethod chanceMethod, int numberOfSets);
    List<Integer>[] generateMainNumbers(String gameName, ChanceMethod chanceMethod, int numberOfSets);
}
