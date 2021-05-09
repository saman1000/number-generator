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
    private Collection<Integer> numbers;

    /**
     * date the numbers were drawn
     */
    private String drawnDate;

    private Integer megaBallNumber;

    private static Integer expectedNumbers = 5;

    public PriorMegaMillionsResult(String drwanDate, List<Integer> drawnNumbers, Integer ballNumber) {
        this.drawnDate = drwanDate;

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
        if (obj != null && obj instanceof PriorMegaMillionsResult) {
            PriorMegaMillionsResult anotherResult = (PriorMegaMillionsResult) obj;
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

