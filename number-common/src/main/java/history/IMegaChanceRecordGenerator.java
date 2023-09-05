package history;

import java.util.List;

public interface IMegaChanceRecordGenerator {
    Integer[] generateBallNumbers(ChanceMethod chanceMethod, int numberOfSets);
    List<Integer>[] generateMainNumbers(ChanceMethod chanceMethod, int numberOfSets);
}
