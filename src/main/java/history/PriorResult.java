package history;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

public class PriorResult implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * minimum value of a number is 1 and maximum value is 49
     */
    private SortedSet<Integer> numbers;

    /**
     * date the numbers were drawn
     */
    private String m_drawnDate;

    public PriorResult(String drwanDate, Integer[] numberArray) {
        m_drawnDate = drwanDate;

        numbers = new TreeSet<Integer>();

        //there is no need to check whether a number is duplicated or not as the
        //sorted set will not add the duplicate numbers

        numbers.addAll(Arrays.asList(numberArray));

        if (numbers.size() != 7) {
            //less than 7 numbers indicate an error
            throw new IllegalStateException(numbers.size() + " is less than 7");
        }
    }

    public Collection<Integer> getNumbers() {
        return numbers;
    }

    public void addResults(Collection<Integer> results) {
        results.addAll(numbers);
    }

    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof PriorResult) {
            PriorResult anotherResult = (PriorResult) obj;
            result = equals(anotherResult);
        }

        return result;
    }

    public boolean equals(PriorResult anotherResult) {
        boolean result = false;
        if (m_drawnDate.equals(anotherResult.m_drawnDate)) {
            if (numbers.equals(anotherResult.numbers)) {
                result = true;
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

