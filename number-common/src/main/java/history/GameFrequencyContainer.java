package history;

import lombok.Getter;

import java.util.List;
import java.util.NavigableMap;
import java.util.function.Consumer;

public class GameFrequencyContainer {

    @Getter
    private long numberOfAcceptedRecords;

    @Getter
    private final GameFrequency frequencyOfMainNumbers;

    @Getter
    private final GameFrequency frequencyOfBallNumbers;

    @Getter
    private final PairingFrequency pairingFrequency;

    public GameFrequencyContainer(int maxMainNumberValue, int maxBallNumberValue) {
        frequencyOfMainNumbers = new GameFrequency(maxMainNumberValue);
        frequencyOfBallNumbers = new GameFrequency(maxBallNumberValue);
        pairingFrequency = new PairingFrequency(maxMainNumberValue);
        numberOfAcceptedRecords = 0;
    }

    public Consumer<List<Integer>> mainNumbersDrawn() {
        return list -> {
            list.forEach(frequencyOfMainNumbers::numberOccurrenceObserved);
            pairingFrequency.parseMainNumbers(list);
        };
    }

    public Consumer<Integer> ballNumberDrawn() {
        return frequencyOfBallNumbers::numberOccurrenceObserved;
    }

    public Integer getBallNumberFrequency(Integer ballNumber) {
        return frequencyOfBallNumbers.getFrequencyOfNumber(ballNumber);
    }

    public Integer getMainNumberFrequency(Integer mainNumber) {
        return frequencyOfMainNumbers.getFrequencyOfNumber(mainNumber);
    }

    public NavigableMap<Integer, Integer> getBallNumberChanceMap(ChanceMethod chanceMethod) {
        switch (chanceMethod) {
            case STRAIGHT -> {
                return frequencyOfBallNumbers.getNumberFrequencies();
            }

            case SWAPPED -> {
                return frequencyOfBallNumbers.getSwappedNumberFrequencies();
            }

            case MIXTURE -> throw new UnsupportedOperationException();

            default -> throw new RuntimeException(chanceMethod.toString());
        }
    }

    public NavigableMap<Integer, Integer> getMainNumbersChanceMap(ChanceMethod chanceMethod) {
        switch (chanceMethod) {
            case STRAIGHT -> {
                return frequencyOfMainNumbers.getNumberFrequencies();
            }

            case SWAPPED -> {
                return frequencyOfMainNumbers.getSwappedNumberFrequencies();
            }

            case MIXTURE -> throw new UnsupportedOperationException();

            default -> throw new RuntimeException(chanceMethod.toString());
        }
    }

    public NavigableMap<Integer, Integer> getMainNumbersChanceMap(Integer number) {
        pairingFrequency.getPairingFrequencies(number);

        return null;
    }

    public void acceptedOneRecord() {
        numberOfAcceptedRecords++;
    }

}
