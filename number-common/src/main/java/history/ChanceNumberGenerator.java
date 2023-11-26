package history;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChanceNumberGenerator {

    private GameFrequency frequencyOfNumbers;
    private PairingFrequency pairingFrequency;

    public Integer generateNumber() {

        return  0;
    }

    public Integer[] generatePairNumbers() {
        return  new Integer[2];
    }

}
