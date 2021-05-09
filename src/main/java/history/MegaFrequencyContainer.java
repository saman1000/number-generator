package history;

import java.util.NavigableMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MegaFrequencyContainer {

    private MegaFrequency frequencyOfMainNumbers;

    private MegaFrequency frequencyOfBallNumbers;

    public MegaFrequencyContainer(int maxMainNumberValue, int maxBallNumberValue) {
        frequencyOfMainNumbers = new MegaFrequency(maxMainNumberValue);
        frequencyOfBallNumbers = new MegaFrequency(maxBallNumberValue);
    }

    public Consumer<Stream<Integer>> mainNumbersConsumer() {
        return stream -> stream.forEach(mainNumber -> frequencyOfMainNumbers.numberOccurrenceObserved(mainNumber));
    }

    public Consumer<Integer> ballNumberConsumer() {
        return ballNumber -> frequencyOfBallNumbers.numberOccurrenceObserved(ballNumber);
    }

    public Integer getBallNumberFrequency(Integer ballNumber) {
        return frequencyOfBallNumbers.getFrequencyOfNumber(ballNumber);
    }

    public Integer getMainNumberFrequency(Integer mainNumber) {
        return frequencyOfMainNumbers.getFrequencyOfNumber(mainNumber);
    }

    public NavigableMap<Integer, Integer> getBallNumberChanceMap() {
        return frequencyOfBallNumbers.getChanceMap();
    }

    public NavigableMap<Integer, Integer> getMainNumbersChanceMap() {
        return frequencyOfMainNumbers.getChanceMap();
    }

}
