package history;

import games.MegaConfig;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.NavigableMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
public class MegaFrequencyContainer {

    @Getter
    private long numberOfAcceptedRecords;

    private final MegaFrequency frequencyOfMainNumbers;

    private final MegaFrequency frequencyOfBallNumbers;

    public MegaFrequencyContainer(MegaConfig megaConfig) {
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
