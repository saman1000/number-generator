package history;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public class ResultCollector implements Serializable {

    public static final long serialVersionUID = 1L;

    private ArrayList<Integer> m_allNumbers;

    private ArrayList<PriorResult> formerResults;

    private String lastStoredDate;

    private NumberFrequency m_frequencies;

    private ArrayList<Integer[]> m_generatedList;

    public ResultCollector() {
        formerResults = new ArrayList<PriorResult>();
        m_allNumbers = new ArrayList<Integer>();
        lastStoredDate = "";
        m_generatedList = new ArrayList<Integer[]>();
        m_frequencies = new NumberFrequency();
    }

    public void addOneResult(String date, Integer[] numbers) {

        if (lastStoredDate.compareTo(date) < 0) {
            formerResults.add(new PriorResult(date, numbers));

            lastStoredDate = date;

            m_allNumbers.addAll(Arrays.asList(numbers));
        }
    }

    public static void addResults(Stream<String[]> results) {
        ResultCollector resultCollector = new ResultCollector();

        results
                .forEach(result -> {
                    String date = result[0];
                    StringTokenizer tokenizer = new StringTokenizer(result[1], ",");
                    ArrayList<Integer> numbers =  new ArrayList<>();
                    while (tokenizer.hasMoreElements()) {
                        numbers.add(Integer.valueOf(tokenizer.nextToken()));
                    }
                    resultCollector.addOneResult(date, numbers.toArray(new Integer[0]));
                })
        ;
    }

    public void allNumbersAreRead() {
        m_frequencies.determineFrequencies(m_allNumbers);
    }

    /**
     *
     * @param number
     *            indicates expected set
     * @throws Exception
     */
    public void generateList(int number) throws Exception {
        if (number > 0) {
            Integer[] oneSet = null;
            ArrayList<Integer[]> setList = new ArrayList<Integer[]>();
            for (int counter = 0; counter < 100; counter++) {
                oneSet = m_frequencies.generateOneSet();
                setList.add(oneSet);
                Thread.sleep(5);
            }

            m_generatedList.addAll(setList);
            List<Integer[]> selectedSets = funnel(number, setList);

            display(selectedSets);
        }
    }

    private void display(List<Integer[]> selectedSets) {
        for (Integer[] set : selectedSets) {
            StringBuffer oneLine = new StringBuffer();
            for (Integer oneNumber : set ) {
                oneLine.append(oneNumber);
                oneLine.append(", ");
            }
            System.out.println(oneLine);
        }
    }

    /**
     *
     */
    private ArrayList<Integer[]> funnel(int number, List<Integer[]> list) {
        ArrayList<Integer[]> result = new ArrayList<Integer[]>(number);

        Random random = new Random();
        int onePosition = 0;
        int maxPosition = list.size() - 1;
        for (int counter = 0; counter < number; counter++) {
            onePosition = random.nextInt(maxPosition);
            result.add(list.get(onePosition));
        }

        return result;
    }

}
