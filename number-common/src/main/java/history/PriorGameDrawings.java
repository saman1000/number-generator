package history;

import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * @param drawnDate date the numbers were drawn
 */
public record PriorGameDrawings(String drawnDate, Collection<Integer> numbers, Integer ballNumber) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Integer expectedNumbers = 5;

    public PriorGameDrawings{
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
                result = true;
            } else {
                throw new IllegalStateException("two results for the same date are not equal, date is " + drawnDate);
            }
        }

        return result;
    }

    public int hashCode() {
        return numbers.hashCode();
    }

    @Override
    public @NonNull String toString() {
        return "Drawn Date: " + drawnDate + " ::" + numbers;
    }
}
