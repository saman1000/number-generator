package history;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class MegaFrequencyContainer {

    private MegaFrequency frequencyOfMainNumbers;

    private MegaFrequency frequencyOfBallNumbers;

    public MegaFrequencyContainer() {
        frequencyOfMainNumbers = new MegaFrequency(70);
        frequencyOfBallNumbers =  new MegaFrequency(25);
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

}
