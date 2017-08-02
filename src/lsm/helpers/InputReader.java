package lsm.helpers;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class InputReader {

    // If this fails we're fucked in the fucked
    private static ConsoleReader console;
    static { try {console = new ConsoleReader();} catch (IOException ignored) {} }

    public static String readConsole () throws IOException {
        return console.next();
    }

    public static String readFile (String path) throws IOException {
        return readReaderAndClose(getFileReader(path));
    }

	public static String readWebsite (String link) throws IOException {
        return readReaderAndClose(getWebsiteReader(link));
	}

    public static String readReader (BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (reader.ready()) {
            sb.append(reader.readLine());
            while (reader.ready())
                sb.append("\n").append(reader.readLine());
        }
        return sb.toString();
    }

    public static BufferedReader getConsoleReader () throws IOException {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    public static BufferedReader getFileReader (String path) throws IOException {
        return new BufferedReader(new FileReader(path));
    }

    public static BufferedReader getWebsiteReader (String link) throws IOException {
        URLConnection conn = new URL(link).openConnection();
        String encoding = conn.getContentEncoding() == null ? "UTF-8" : conn.getContentEncoding();
        return new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
    }

    private static String readReaderAndClose (BufferedReader reader) throws IOException {
        String res = readReader(reader);
        reader.close();
        return res;
    }
}

class ConsoleReader {
    private BufferedReader reader = InputReader.getConsoleReader();
    ConsoleReader() throws IOException {}
    String next () throws IOException {
        while (!reader.ready()) {}
        return InputReader.readReader(reader);
    }
    void close () throws IOException {
        reader.close();
    }
    BufferedReader getReader () {
        return reader;
    }
}
