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
        NavigableMap<Integer, Integer> numberFrequencies;
        if (ChanceMethod.STRAIGHT.equals(chanceMethod)) {
            numberFrequencies = frequencyOfBallNumbers.getNumberFrequencies();
        } else if (ChanceMethod.SWAPPED.equals(chanceMethod)) {
            numberFrequencies = frequencyOfBallNumbers.getSwappedNumberFrequencies();
        } else {
            throw new RuntimeException("not supporting " + chanceMethod);
        }

        int total = numberFrequencies.lastKey();
        return numberFrequencies.ceilingEntry(numberSelector.nextInt(total)).getValue();
    }

    public List<Integer> generateMainNumberSet(ChanceMethod chanceMethod, int setSize) {
        List<Integer> numberSet = new ArrayList<>();

        NavigableMap<Integer, Integer> numberFrequencies;
        if (ChanceMethod.STRAIGHT.equals(chanceMethod)) {
            numberFrequencies = frequencyOfMainNumbers.getNumberFrequencies();
        } else if (ChanceMethod.SWAPPED.equals(chanceMethod)) {
            numberFrequencies = frequencyOfMainNumbers.getSwappedNumberFrequencies();
        } else {
            throw new RuntimeException("not supporting " + chanceMethod);
        }

        int total = numberFrequencies.lastKey();
        //select first random number based on frequency of all numbers
        Integer oneNumber = numberFrequencies.ceilingEntry(numberSelector.nextInt(total)).getValue();
        numberSet.add(oneNumber);
        //select remaining random numbers using frequency of paired numbers
        while (numberSet.size() < setSize) {
            addOneRandomNumberUsingPairFrequency(chanceMethod, oneNumber, numberSet);
        }

        Collections.sort(numberSet);
        return numberSet;
    }

    private void addOneRandomNumberUsingPairFrequency(ChanceMethod chanceMethod, Integer oneNumber, List<Integer> numberSet) {
        NavigableMap<Integer, Integer> numberFrequencies;
        if (ChanceMethod.STRAIGHT.equals(chanceMethod)) {
            numberFrequencies = GameFrequency.generateFrequencyMap(pairingFrequency.getPairingFrequencies(oneNumber));
        } else if (ChanceMethod.SWAPPED.equals(chanceMethod)) {
            numberFrequencies = GameFrequency.generateSwappedFrequencyMap(pairingFrequency.getPairingFrequencies(oneNumber));
        } else {
            throw new RuntimeException("not supporting " + chanceMethod);
        }

        int total = numberFrequencies.lastKey();
        do {
            oneNumber = numberFrequencies.ceilingEntry(numberSelector.nextInt(total)).getValue();
        } while (numberSet.contains(oneNumber));
        numberSet.add(oneNumber);
    }


}
