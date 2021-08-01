package history;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PriorMegaMillionsResult implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * minimum value of a number is 1 and maximum value is 49
     */
    private final Collection<Integer> numbers;

    /**
     * date the numbers were drawn
     */
    private final String drawnDate;

    private final Integer megaBallNumber;

    private static final Integer expectedNumbers = 5;

    public PriorMegaMillionsResult(String drawnDate, List<Integer> drawnNumbers, Integer ballNumber) {
        this.drawnDate = drawnDate;

        //there is no need to check whether a number is duplicated or not as the
        //sorted set will not add the duplicate numbers
        megaBallNumber = ballNumber;
        numbers = Collections.unmodifiableList(drawnNumbers);
        if (numbers.size() != PriorMegaMillionsResult.expectedNumbers) {
            throw new IllegalStateException(String.format(
                    "%s is less than %s", numbers.size(), PriorMegaMillionsResult.expectedNumbers
            ));
        }
    }

    public Collection<Integer> getNumbers() {
        return numbers;
    }

    public String getDrawnDate() {
        return drawnDate;
    }

    public Integer getMegaBallNumber() {
        return megaBallNumber;
    }

    public void addResults(Collection<Integer> results) {
        results.addAll(numbers);
    }

    public boolean equals(Object obj) {
        if (obj instanceof PriorMegaMillionsResult anotherResult) {
            return equals(anotherResult);
        }

        return false;
    }

    public boolean equals(PriorMegaMillionsResult anotherResult) {
        boolean result = false;

        if (drawnDate.equals(anotherResult.drawnDate)) {
            if (numbers.equals(anotherResult.numbers) && megaBallNumber.equals(anotherResult.megaBallNumber)) {
                result =  true;
            } else {
                throw new IllegalStateException("two results for the same date are not equal, date is " + drawnDate);
            }
        }

        return result;
    }

    public int hashCode() {
        return numbers.hashCode();
    }

    public String toString() {
        return "Drawn Date: " + drawnDate + " ::" + numbers;
    }
}

