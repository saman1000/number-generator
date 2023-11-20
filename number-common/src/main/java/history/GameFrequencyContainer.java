package history;

import lombok.Getter;

import java.util.List;
import java.util.NavigableMap;
import java.util.function.Consumer;

public class GameFrequencyContainer {

    @Getter
    private long numberOfAcceptedRecords;

    private final GameFrequency frequencyOfMainNumbers;

    private final GameFrequency frequencyOfBallNumbers;

    private final PairingFrequency pairingFrequency;

    public GameFrequencyContainer(int maxMainNumberValue, int maxBallNumberValue) {
        frequencyOfMainNumbers = new GameFrequency(maxMainNumberValue);
        frequencyOfBallNumbers = new GameFrequency(maxBallNumberValue);
        pairingFrequency = new PairingFrequency(maxMainNumberValue);
        numberOfAcceptedRecords = 0;
    }

    public Consumer<List<Integer>> mainNumbersDrawn() {
        return stream -> stream.forEach(frequencyOfMainNumbers::numberOccurrenceObserved);
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
                return frequencyOfBallNumbers.getChanceMap();
            }

            case SWAPPED -> {
                return frequencyOfBallNumbers.getSwappedChanceMap();
            }

            case MIXTURE -> throw new UnsupportedOperationException();

            default -> throw new RuntimeException(chanceMethod.toString());
        }
    }

    public NavigableMap<Integer, Integer> getMainNumbersChanceMap(ChanceMethod chanceMethod) {
        switch (chanceMethod) {
            case STRAIGHT -> {
                return frequencyOfMainNumbers.getChanceMap();
            }

            case SWAPPED -> {
                return frequencyOfMainNumbers.getSwappedChanceMap();
            }

            case MIXTURE -> throw new UnsupportedOperationException();

            default -> throw new RuntimeException(chanceMethod.toString());
        }
    }

    public void acceptedOneRecord() {
        numberOfAcceptedRecords++;
    }

}
