package history;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class PriorGameDrawings implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Collection<Integer> numbers;

    /**
     * date the numbers were drawn
     */
    private final String drawnDate;

    private final Integer ballNumber;

    private static final Integer expectedNumbers = 5;

    public PriorGameDrawings(String drawnDate, List<Integer> drawnNumbers, Integer ballNumber) {
        this.drawnDate = drawnDate;

        //there is no need to check whether a number is duplicated or not as the
        //sorted set will not add the duplicate numbers
        this.ballNumber = ballNumber;
        numbers = Collections.unmodifiableList(drawnNumbers);
        if (numbers.size() != PriorGameDrawings.expectedNumbers) {
            throw new IllegalStateException(String.format(
                    "%s is less than %s", numbers.size(), PriorGameDrawings.expectedNumbers
            ));
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof PriorGameDrawings anotherResult) {
            return equals(anotherResult);
        }

        return false;
    }

    public boolean equals(PriorGameDrawings anotherResult) {
        boolean result = false;

        if (drawnDate.equals(anotherResult.drawnDate)) {
            if (numbers.equals(anotherResult.numbers) && ballNumber.equals(anotherResult.ballNumber)) {
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

