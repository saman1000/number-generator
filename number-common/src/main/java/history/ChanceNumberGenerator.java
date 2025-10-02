package history;

import java.util.*;

public class ChanceNumberGenerator {

    final private GameFrequency frequencyOfBallNumbers;
    final private GameFrequency frequencyOfMainNumbers;
    final private PairingFrequency pairingFrequency;

    final private Random numberSelector;

    public ChanceNumberGenerator(
            GameFrequency frequencyOfBallNumbers,
            GameFrequency frequencyOfMainNumbers,
            PairingFrequency pairingFrequency
    ) {
        this.frequencyOfBallNumbers = frequencyOfBallNumbers;
        this.frequencyOfMainNumbers = frequencyOfMainNumbers;
        this.pairingFrequency = pairingFrequency;

        numberSelector = new Random();
    }

    public Integer generateBallNumber(ChanceMethod chanceMethod) {
        NavigableMap<Integer, Integer> numberFrequencies = switch (chanceMethod) {
            case STRAIGHT -> frequencyOfBallNumbers.getNumberFrequencies();
            case SWAPPED -> frequencyOfBallNumbers.getSwappedNumberFrequencies();
            default -> throw new GameException("not supporting " + chanceMethod);
        };

        //select the based on highest frequency
        int total = numberFrequencies.lastKey();
        return numberFrequencies.ceilingEntry(numberSelector.nextInt(total)).getValue();
    }

    public List<Integer> generateMainNumberSet(ChanceMethod chanceMethod, int setSize) {
        List<Integer> numberSet = new ArrayList<>();

        NavigableMap<Integer, Integer> numberFrequencies = switch (chanceMethod) {
            case STRAIGHT -> frequencyOfMainNumbers.getNumberFrequencies();
            case SWAPPED -> frequencyOfMainNumbers.getSwappedNumberFrequencies();
            default -> throw new GameException("not supporting " + chanceMethod);
        };

        int total = numberFrequencies.lastKey();
        //select first random number based on frequency of all numbers
        Integer oneNumber = numberFrequencies.ceilingEntry(numberSelector.nextInt(total)).getValue();
        numberSet.add(oneNumber);
        //select remaining random numbers using frequency of paired numbers
        while (numberSet.size() < setSize) {
            oneNumber = generateOneRandomNumberUsingPairFrequency(chanceMethod, oneNumber, numberSet);
            numberSet.add(oneNumber);
        }

        Collections.sort(numberSet);
        return numberSet;
    }

    private Integer generateOneRandomNumberUsingPairFrequency(
            ChanceMethod chanceMethod, Integer oneNumber, List<Integer> numberSet) {
        NavigableMap<Integer, Integer> numberFrequencies = switch (chanceMethod) {
            case STRAIGHT -> GameFrequency.generateFrequencyMap(pairingFrequency.getPairingFrequencies(oneNumber));
            case SWAPPED -> GameFrequency.generateSwappedFrequencyMap(pairingFrequency.getPairingFrequencies(oneNumber));
            default -> throw new GameException("not supporting " + chanceMethod);
        };

        int total = numberFrequencies.lastKey();
        do {
            oneNumber = numberFrequencies.ceilingEntry(numberSelector.nextInt(total)).getValue();
        } while (numberSet.contains(oneNumber));

        return oneNumber;
    }


}
