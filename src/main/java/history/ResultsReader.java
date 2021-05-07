package history;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

/**
 * Read all the valid lines
 * create frequency for each play number
 * create frequency for each ball number
 */
public class ResultsReader {

    private ResultCollector collector;

    private MegaExtractor extractor;

    private MegaFrequency megaFrequency;

    private MegaFrequencyContainer megaFrequencyContainer;

    private String m_filePath = "~/development/lottery/storedresults";

    ResultsReader() {
        extractor = new MegaExtractor();
        megaFrequency = new MegaFrequency(70);
        megaFrequencyContainer = new MegaFrequencyContainer(70, 25);
        try {
            if (!readFromFile(m_filePath)) {
                collector = new ResultCollector();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static MegaFrequencyContainer readLinesUsingScanner(Readable readable, String patternString) {
        ResultsReader resultsReader =  new ResultsReader();
        try (Scanner scanner = new Scanner(readable);) {
            scanner
                    .findAll(patternString)
                    .forEach(matchResult -> {
                        resultsReader.updateFrequency(matchResult);
                    })
            ;
        }
        return resultsReader.megaFrequencyContainer;
    }

    private void updateFrequency(MatchResult matchResult) {
        String[] groups = new String[matchResult.groupCount()];
        for (int counter = groups.length - 1; counter >= 0; counter--) {
            groups[counter] = matchResult.group(counter + 1).trim();
        }

        megaFrequencyContainer.mainNumbersConsumer().accept(MegaExtractor.getMainNumbers(groups[1]));
        megaFrequencyContainer.ballNumberConsumer().accept(
                MegaExtractor.getBallNumber(groups[2])
                        .orElseThrow(
                                () -> new IllegalStateException(String.format("No valid ball number: %s", groups[2])))
        );
    }

    private PriorMegaMillionsResult addOneResult(MatchResult matchResult) {
        String[] groups = new String[matchResult.groupCount()];
        for (int counter = groups.length - 1; counter >= 0; counter--) {
            groups[counter] = matchResult.group(counter + 1).trim();
        }

        return extractor.extractResult(groups);
    }

    public void loadDatasource() throws Exception {
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection conn = DriverManager
                .getConnection("jdbc:odbc:lotteryResults");
        ResultSet rs = null;

        Statement stmt = conn.createStatement();

        rs = stmt.executeQuery("SELECT * FROM [results$]");

        String date = null;
        Integer[] numbers = new Integer[7];
        while (rs.next()) {
            date = rs.getString(1);
            numbers[0] = rs.getInt(2);
            numbers[1] = rs.getInt(3);
            numbers[2] = rs.getInt(4);
            numbers[3] = rs.getInt(5);
            numbers[4] = rs.getInt(6);
            numbers[5] = rs.getInt(7);
            numbers[6] = rs.getInt(8);

            collector.addOneResult(date, numbers);
        }

        rs.close();
        stmt.close();
        conn.close();

        writeToFile(m_filePath);

        calculateFrequencies();

        generateNumbers();
    }

    private void calculateFrequencies() {
        collector.allNumbersAreRead();
    }

    private void generateNumbers() throws Exception {
        collector.generateList(2);
    }

    private boolean readFromFile(String filePath) throws Exception {
        boolean readFromFile = false;

        File theFile = new File(filePath);
        if (theFile.exists() && theFile.isFile()) {
            FileInputStream fis = new FileInputStream(theFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();

            collector = (ResultCollector) obj;

            ois.close();

            readFromFile = true;
        }

        return readFromFile;
    }

    private void writeToFile(String filePath) throws Exception {
        File theFile = new File(filePath);
        FileOutputStream fos = new FileOutputStream(theFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(collector);
        oos.close();
    }
}

