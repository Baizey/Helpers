package lsm.helpers.IO.read.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static lsm.helpers.IO.Utils.isWebsite;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TextReader {


    /////////////////////////////////////
    // Reader functions
    /////////////////////////////////////
    public static List<String> read(String from) throws IOException {
        return isWebsite(from) ? readWebsite(from) : readFile(from);
    }

    public static List<String> readFile(String path) throws IOException {
        return readReaderAndClose(getTextReader(path));
    }

    public static List<String> readWebsite(String link) throws IOException {
        return readReaderAndClose(getWebsiteReader(link));
    }

    /////////////////////////////////////
    // Special reader functions
    /////////////////////////////////////
    public static int[][] readAsIntMatrix(String from) throws IOException {
        var input = read(from);
        var matrix = new int[input.size()][];
        String[] t;
        for (int i = 0; i < matrix.length; i++) {
            t = input.get(i).replaceAll("\\D+", " ").trim().split(" ");
            matrix[i] = new int[t.length];
            for (int j = 0; j < t.length; j++)
                matrix[i][j] = Integer.parseInt(t[j]);
        }
        return matrix;
    }

    public static double[][] readAsDoubleMatrix(String from) throws IOException {
        var input = read(from);
        var matrix = new double[input.size()][];
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
    public static Scanner getConsoleReader() {
        return new Scanner(System.in);
    }

    public static BufferedReader getReader(String what) throws IOException {
        return isWebsite(what) ? getWebsiteReader(what) : getTextReader(what);
    }
    
    public static BufferedReader getTextReader(String path) throws IOException {
        return new BufferedReader(new FileReader(Paths.get(path).toFile()));
    }

    public static BufferedReader getWebsiteReader(String link) throws IOException {
        var conn = new URL(link).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla");
        var encoding = conn.getContentEncoding() == null ? "UTF-8" : conn.getContentEncoding();
        return new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
    }

    /////////////////////////////////////
    // Private utility functions
    /////////////////////////////////////
    private static List<String> readReaderAndClose(BufferedReader reader) throws IOException {
        var lines = readReader(reader);
        reader.close();
        return lines;
    }

    private static List<String> readReader(BufferedReader reader) {
        return reader.lines().collect(Collectors.toList());
    }
    
}
