package history;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PriorMegaMillionsResult implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * minimum value of a number is 1 and maximum value is 49
     */
    private SortedSet<Integer> numbers;

    /**
     * date the numbers were drawn
     */
    private String m_drawnDate;

    private Integer megaBallNumber;

    private static Integer expectedNumbers = 5;

    public PriorMegaMillionsResult(String drwanDate, Stream<Integer> drawnNumbers, Integer ballNumber) {
        m_drawnDate = drwanDate;

        //there is no need to check whether a number is duplicated or not as the
        //sorted set will not add the duplicate numbers
        megaBallNumber = ballNumber;
        numbers = Collections.unmodifiableSortedSet(new TreeSet<>(drawnNumbers.collect(Collectors.toList())));
        if (numbers.size() != PriorMegaMillionsResult.expectedNumbers) {
            throw new IllegalStateException(String.format(
                    "%s is less than %s", numbers.size(), PriorMegaMillionsResult.expectedNumbers
            ));
        }
    }

    public Collection<Integer> getNumbers() {
        return numbers;
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

        if (m_drawnDate.equals(anotherResult.m_drawnDate)) {
            if (numbers.equals(anotherResult.numbers) && megaBallNumber.equals(anotherResult.megaBallNumber)) {
                result =  true;
            } else {
                throw new IllegalStateException("two results for the same date are not equal, date is " + m_drawnDate);
            }
        }

        return result;
    }

    public int hashCode() {
        return numbers.hashCode();
    }

    public String toString() {
        return "Drawn Date: " + m_drawnDate + " ::" + numbers;
    }
}

