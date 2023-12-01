package history;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

public class ChanceNumberGeneratorTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testCorrectBallNumberFrequencyIsUsed() {
        GameFrequency mockedFrequencyOfBallNumbers = Mockito.mock(GameFrequency.class);

        NavigableMap<Integer, Integer> mockedBallNumberFrequencies = Mockito.mock(NavigableMap.class);
        Mockito.when(mockedBallNumberFrequencies.lastKey()).thenReturn(100);
        Mockito.when(mockedBallNumberFrequencies.ceilingEntry(Mockito.any()))
                .thenReturn(
                        Mockito.mock(Map.Entry.class)
                );
        Mockito
                .when(mockedFrequencyOfBallNumbers.getNumberFrequencies())
                .thenReturn(mockedBallNumberFrequencies);
        Mockito
                .when(mockedFrequencyOfBallNumbers.getSwappedNumberFrequencies())
                .thenReturn(mockedBallNumberFrequencies);

        GameFrequency mockedFrequencyOfMainNumbers = Mockito.mock(GameFrequency.class);
        PairingFrequency mockedPairingFrequency = Mockito.mock(PairingFrequency.class);

        ChanceNumberGenerator chanceNumberGenerator = new ChanceNumberGenerator(
                mockedFrequencyOfBallNumbers,
                mockedFrequencyOfMainNumbers,
                mockedPairingFrequency
        );
        chanceNumberGenerator.generateBallNumber(ChanceMethod.STRAIGHT);
        Mockito
                .verify(mockedFrequencyOfBallNumbers, Mockito.times(1))
                .getNumberFrequencies();

        chanceNumberGenerator.generateBallNumber(ChanceMethod.SWAPPED);
        Mockito
                .verify(mockedFrequencyOfBallNumbers, Mockito.times(1))
                .getSwappedNumberFrequencies();

        Assertions.assertThrows(
                RuntimeException.class,
                () -> chanceNumberGenerator.generateBallNumber(ChanceMethod.MIXTURE)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCorrectMainNumberFrequencyIsUsed() {
        GameFrequency mockedFrequencyOfBallNumbers = Mockito.mock(GameFrequency.class);
        PairingFrequency mockedPairingFrequency = Mockito.mock(PairingFrequency.class);
        Mockito.when(
                mockedPairingFrequency
                        .getPairingFrequencies(Mockito.anyInt())).thenReturn(new Integer[] {10, 20, 30, 40, 50}
        );

        GameFrequency mockedFrequencyOfMainNumbers = Mockito.mock(GameFrequency.class);
        NavigableMap<Integer, Integer> mockedMainNumberFrequencies = Mockito.mock(NavigableMap.class);
        Mockito.when(mockedMainNumberFrequencies.lastKey()).thenReturn(100);
        Map.Entry<Integer, Integer> mockedEntry = Mockito.mock(Map.Entry.class);
        Mockito.when(mockedEntry.getValue()).thenReturn(2, 3, 4, 5, 6);
        Mockito.when(mockedMainNumberFrequencies.ceilingEntry(Mockito.any())).thenReturn(mockedEntry);
        Mockito
                .when(mockedFrequencyOfMainNumbers.getNumberFrequencies())
                .thenReturn(mockedMainNumberFrequencies);
        Mockito
                .when(mockedFrequencyOfMainNumbers.getSwappedNumberFrequencies())
                .thenReturn(mockedMainNumberFrequencies);

        ChanceNumberGenerator chanceNumberGenerator = new ChanceNumberGenerator(
                mockedFrequencyOfBallNumbers,
                mockedFrequencyOfMainNumbers,
                mockedPairingFrequency
        );
        List<Integer> mainNumberSet = chanceNumberGenerator.generateMainNumberSet(ChanceMethod.SWAPPED, 5);
        Assertions.assertEquals(5, mainNumberSet.size());
        Mockito
                .verify(mockedFrequencyOfMainNumbers, Mockito.times(1))
                .getSwappedNumberFrequencies();

        mainNumberSet = chanceNumberGenerator.generateMainNumberSet(ChanceMethod.STRAIGHT, 1);
        Assertions.assertEquals(1, mainNumberSet.size());
        Mockito
                .verify(mockedFrequencyOfMainNumbers, Mockito.times(1))
                .getNumberFrequencies();

        Assertions.assertThrows(
                RuntimeException.class,
                () -> chanceNumberGenerator.generateMainNumberSet(ChanceMethod.MIXTURE, 1)
        );
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPairingFrequenciesAreUsed() {
        GameFrequency mockedFrequencyOfBallNumbers = Mockito.mock(GameFrequency.class);
        GameFrequency mockedFrequencyOfMainNumbers = Mockito.mock(GameFrequency.class);
        NavigableMap<Integer, Integer> mockedMainNumberFrequencies = Mockito.mock(NavigableMap.class);
        Mockito.when(mockedMainNumberFrequencies.lastKey()).thenReturn(100);
        Map.Entry<Integer, Integer> mockedEntry = Mockito.mock(Map.Entry.class);
        Mockito.when(mockedEntry.getValue()).thenReturn(2, 3, 4, 5);
        Mockito.when(mockedMainNumberFrequencies.ceilingEntry(Mockito.any())).thenReturn(mockedEntry);
        Mockito
                .when(mockedFrequencyOfMainNumbers.getNumberFrequencies())
                .thenReturn(mockedMainNumberFrequencies);

        PairingFrequency mockedPairingFrequency = Mockito.mock(PairingFrequency.class);
        Mockito.when(mockedPairingFrequency.getPairingFrequencies(Mockito.anyInt())).thenReturn(new Integer[] {1});

        ChanceNumberGenerator chanceNumberGenerator = new ChanceNumberGenerator(
                mockedFrequencyOfBallNumbers,
                mockedFrequencyOfMainNumbers,
                mockedPairingFrequency
        );
        chanceNumberGenerator.generateMainNumberSet(ChanceMethod.STRAIGHT, 2);
        Mockito.verify(mockedPairingFrequency, Mockito.times(1)).getPairingFrequencies(Mockito.anyInt());
    }
}
