package history;

import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;

@Getter
public class GameFrequencyContainer {

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

    public void acceptedOneRecord() {
        numberOfAcceptedRecords++;
    }

}
