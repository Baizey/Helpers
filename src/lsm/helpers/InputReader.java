package lsm.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
public class InputReader {
    private static BufferedReader consoleReader;

    static {
        try {
            consoleReader = getConsoleReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /////////////////////////////////////
    // Reader functions
    /////////////////////////////////////
    public static ArrayList<String> read() throws IOException {
        return read(null);
    }

    public static ArrayList<String> read(String what) throws IOException {
        if (what == null)
            return readConsole();
        if (what.matches("^https?://.*"))
            return readWebsite(what);
        return readFile(what);
    }

    private static ArrayList<String> readConsole() throws IOException {
        String line;
        //noinspection StatementWithEmptyBody
        while ((line = consoleReader.readLine()) == null) { }
        ArrayList<String> res = new ArrayList<>();
        res.add(line);
        while ((line = consoleReader.readLine()) != null)
            res.add(line);
        return res;
    }

    private static ArrayList<String> readFile(String path) throws IOException {
        return readReaderAndClose(getFileReader(path));
    }

    private static ArrayList<String> readWebsite(String link) throws IOException {
        return readReaderAndClose(getWebsiteReader(link));
    }

    /////////////////////////////////////
    // Special reader functions
    /////////////////////////////////////
    public static int[][] readAsIntMatrix () throws IOException {
        return readAsIntMatrix(null);
    }
    public static int[][] readAsIntMatrix (String what) throws IOException {
        ArrayList<String> input = read(what);
        int[][] matrix = new int[input.size()][];
        String[] t;
        for (int i = 0; i < matrix.length; i++) {
            t = input.get(i).replaceAll("\\D+", " ").trim().split(" ");
            matrix[i] = new int[t.length];
            for (int j = 0; j < t.length; j++)
                matrix[i][j] = Integer.parseInt(t[j]);
        }
        return matrix;
    }
    public static double[][] readAsDoubleMatrix () throws IOException {
        return readAsDoubleMatrix(null);
    }
    public static double[][] readAsDoubleMatrix (String what) throws IOException {
        ArrayList<String> input = read(what);
        double[][] matrix = new double[input.size()][];
        String[] t;
        for (int i = 0; i < matrix.length; i++) {
            t = input.get(i).replaceAll("[^0-9.]+", " ").trim().split(" ");
            matrix[i] = new double[t.length];
            for (int j = 0; j < t.length; j++)
                matrix[i][j] = Double.parseDouble(t[j]);
        }
        return matrix;
    }

    /////////////////////////////////////
    // Reader constructors
    /////////////////////////////////////
    public static BufferedReader getReader(String what) throws IOException {
        if (what == null)
            return getConsoleReader();
        if (what.matches("^https?://.*\\..*"))
            return getWebsiteReader(what);
        return getFileReader(what);
    }

    private static BufferedReader getConsoleReader() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private static BufferedReader getFileReader(String path) throws IOException {
        return new BufferedReader(new FileReader(path));
    }

    private static BufferedReader getWebsiteReader(String link) throws IOException {
        URLConnection conn = new URL(link).openConnection();
        String encoding = conn.getContentEncoding() == null ? "UTF-8" : conn.getContentEncoding();
        return new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
    }

    /////////////////////////////////////
    // Private utility functions
    /////////////////////////////////////

    private static ArrayList<String> readReaderAndClose(BufferedReader reader) throws IOException {
        ArrayList<String> res = readReader(reader);
        reader.close();
        return res;
    }

    private static ArrayList<String> readReader(BufferedReader reader) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        reader.lines().forEach(lines::add);
        return lines;
    }
}
