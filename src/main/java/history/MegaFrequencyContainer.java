package history;

import mega.Config;
import org.springframework.stereotype.Component;

import java.util.NavigableMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
public class MegaFrequencyContainer {

    private long numberOfAcceptedRecords;

    private final MegaFrequency frequencyOfMainNumbers;

    private final MegaFrequency frequencyOfBallNumbers;

    public MegaFrequencyContainer(Config megaConfig) {
        frequencyOfMainNumbers = new MegaFrequency(megaConfig.getMaxMainNumberValue());
        frequencyOfBallNumbers = new MegaFrequency(megaConfig.getMaxBallNumberValue());
        numberOfAcceptedRecords = 0;
    }

    public Consumer<Stream<Integer>> mainNumbersDrawn() {
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

    public NavigableMap<Integer, Integer> getBallNumberChanceMap() {
        return frequencyOfBallNumbers.getChanceMap();
    }

    public NavigableMap<Integer, Integer> getMainNumbersChanceMap() {
        return frequencyOfMainNumbers.getSwappedChanceMap();
    }

    public void acceptedOneRecord() {
        numberOfAcceptedRecords++;
    }

    public long getNumberOfAcceptedRecords() {
        return numberOfAcceptedRecords;
    }

}
