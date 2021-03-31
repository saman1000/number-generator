package history;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ResultsReader {

    private ResultCollector collector;

    private String m_filePath = "E:\\development\\lottery\\storedresults";

    ResultsReader() {
        try {
            if (!readFromFile(m_filePath)) {
                collector = new ResultCollector();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static List<String[]> readUsingScanner(Readable readable, String patternString) {
        List<String[]> result = Collections.<String[]>emptyList();

        try (Scanner scanner = new Scanner(readable);) {
            result = scanner
                    .findAll(patternString)
                    .map(matchResult -> {
                        String[] groups = new String[matchResult.groupCount()];
                        for (int counter = groups.length - 1; counter >= 0; counter--) {
                            groups[counter] = matchResult.group(counter + 1).trim();
                        }
                        return groups;
                    })
                    .collect(Collectors.toList())
            ;
        }

        return result;
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

