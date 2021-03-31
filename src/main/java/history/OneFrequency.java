package history;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class OneFrequency implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer m_watchedNumber;

    private int frequency;

    public OneFrequency(int watchedNumber) {
        m_watchedNumber = Integer.valueOf(watchedNumber);
    }

    public void updateFrequency(Collection<Integer> priorResults) {
        frequency += Collections.frequency(priorResults, m_watchedNumber);
    }

    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof OneFrequency) {
            OneFrequency otherObj = (OneFrequency) obj;
            result = equals(otherObj);
        }

        return result;
    }

    public void positionNumber(Collection<Integer> numbers) {
        for (int counter = 0; counter <=frequency; counter++) {
            numbers.add(m_watchedNumber);
        }
    }

    public void reset() {
        frequency = 0;
    }

    public boolean equals(OneFrequency anotherObj) {
        //they are equal if their watched numbers are equal
        return m_watchedNumber.equals(anotherObj.m_watchedNumber);
    }

    public int hashCode() {
        return m_watchedNumber + (frequency % 49);
    }

    public String toString() {
        return "frequency of " + m_watchedNumber + " is " + frequency;
    }
}
