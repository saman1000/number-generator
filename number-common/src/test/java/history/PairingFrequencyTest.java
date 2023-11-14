package history;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PairingFrequencyTest {

    @Test
    void testSmallPairs() {
        PairingFrequency pairingFrequency = new PairingFrequency(5);
        pairingFrequency.parseMainNumbers(new int[] {1, 2, 3});
        pairingFrequency.parseMainNumbers(new int[] {1, 2, 4});
        pairingFrequency.parseMainNumbers(new int[] {1, 2, 5});
        assertArrayEquals(new int[] {0, 3, 1, 1, 1}, pairingFrequency.getPairingFrequencies(1));
        assertArrayEquals(new int[] {3, 0, 1, 1, 1}, pairingFrequency.getPairingFrequencies(2));
        assertArrayEquals(new int[] {1, 1, 0, 0, 0}, pairingFrequency.getPairingFrequencies(3));
        assertArrayEquals(new int[] {1, 1, 0, 0, 0}, pairingFrequency.getPairingFrequencies(4));
        assertArrayEquals(new int[] {1, 1, 0, 0, 0}, pairingFrequency.getPairingFrequencies(5));
    }

    @Test
    void testBigPairs() {
        PairingFrequency pairingFrequency = new PairingFrequency(70);
        pairingFrequency.parseMainNumbers(new int[] {6,18,44,46,68});
        pairingFrequency.parseMainNumbers(new int[] {3,8,17,46,63});
        pairingFrequency.parseMainNumbers(new int[] {12,24,46,57,66});
        pairingFrequency.parseMainNumbers(new int[] {3,19,32,39,59});
        pairingFrequency.parseMainNumbers(new int[] {18,40,47,55,64});
        pairingFrequency.parseMainNumbers(new int[] {15,30,35,42,60});
        pairingFrequency.parseMainNumbers(new int[] {10,13,14,57,66});
        pairingFrequency.parseMainNumbers(new int[] {6,9,13,29,66});

        assertEquals(0, pairingFrequency.getPairingFrequencies(6)[69]);
        assertEquals(0, pairingFrequency.getPairingFrequencies(6)[0]);
        assertEquals(1, pairingFrequency.getPairingFrequencies(6)[8]);
        assertEquals(2, pairingFrequency.getPairingFrequencies(66)[12]);
        assertEquals(2, pairingFrequency.getPairingFrequencies(57)[65]);
    }
}
