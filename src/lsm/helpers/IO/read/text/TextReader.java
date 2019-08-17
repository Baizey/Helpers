package lsm.helpers.IO.read.text;

import lsm.helpers.IO.read.Source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static lsm.helpers.IO.Utils.isWebsite;
import static lsm.helpers.IO.read.Source.File;
import static lsm.helpers.IO.read.Source.Website;

public class TextReader {
    private List<String> lines;

    public static TextReader read(String from) throws IOException {
        return new TextReader(from);
    }

    public static TextReader read(String from, Source source) throws IOException {
        return new TextReader(from, source);
    }

    public TextReader(String from) throws IOException {
        this(from, isWebsite(from) ? Website : File);
    }

    public TextReader(String from, Source source) throws IOException {
        BufferedReader reader;
        switch (source) {
            case File:
                reader = new BufferedReader(new FileReader(Paths.get(from).toFile()));
                break;
            case Website:
                var conn = new URL(from).openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla");
                var encoding = conn.getContentEncoding() == null ? "UTF-8" : conn.getContentEncoding();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
                break;
            default:
                throw new IllegalArgumentException("Source is unknown");
        }
        this.lines = reader.lines().collect(Collectors.toList());
        reader.close();
    }

    public String asString() {
        return String.join("\n", lines);
    }

    public List<String> asLines() {
        return new ArrayList<>(lines);
    }

    public int[][] asIntMatrix() {
        return lines.stream()
                       .map(e -> e.replaceAll("\\D+", " ").trim().split(" +"))
                       .map(e -> Arrays.stream(e).mapToInt(Integer::parseInt).toArray())
                       .toArray(int[][]::new);
    }

    public double[][] asDoubleMatrix() {
        return lines.stream()
                       .map(e -> e.replaceAll("[^0-9.]+", " ").trim().split(" +"))
                       .map(e -> Arrays.stream(e).mapToDouble(Double::parseDouble).toArray())
                       .toArray(double[][]::new);
    }
}
