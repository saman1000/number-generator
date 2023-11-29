package history;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;

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
        Integer oneNumber;
        while (numberSet.size() < setSize) {
            oneNumber = numberFrequencies.ceilingEntry(numberSelector.nextInt(total)).getValue();
            if (!numberSet.contains(oneNumber)) {
                numberSet.add(oneNumber);
            }
        }

        return numberSet;
    }

}
