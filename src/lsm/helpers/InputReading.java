package lsm.helpers;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class InputReading{
	
	// Read website
	public static ArrayList<String> readWebsite(String link) throws IOException{
		URLConnection conn = new URL(link).openConnection();
		InputStream in = conn.getInputStream();
		String encoding = conn.getContentEncoding() == null ? "UTF-8" : conn.getContentEncoding();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int len;
		while ((len = in.read(buf)) != -1) baos.write(buf, 0, len);
		String body = new String(baos.toByteArray(), encoding);
		ArrayList<String> result = new ArrayList<>();
		String[] text = body.split((body.contains("\r\n")) ? "\r\n" : "\n");
		Collections.addAll(result, text);
		in.close();
		return result;
	}
	
	// Read File
	public static ArrayList<String> readFile(String path) throws FileNotFoundException{
		return read(new File(path));
	}
	
	// Read console
    public static String readConsoleLine() throws IOException {
        return new Scanner(System.in).nextLine();
    }
    public static ArrayList<String> readConsole(int lineCount) throws IOException {
        return readConsole("", lineCount);
    }
    public static ArrayList<String> readConsole(String exiton) throws IOException{
        return readConsole(exiton, Integer.MAX_VALUE - 1);
    }
	public static ArrayList<String> readConsole(String exiton, int lineCount) throws IOException {
		ArrayList<String> result = new ArrayList<>();
		Scanner s = new Scanner(System.in);
		String line;
		int c = 0;
		while(s.hasNext() && !(line = s.nextLine()).equals(exiton) && (c < lineCount)){
            result.add(line);
            c++;
		}
		s.close();
		return result;
	}
	
	// Used by file reader
	private static ArrayList<String> read(Scanner sc){
		ArrayList<String> result = new ArrayList<>();
		while(sc.hasNextLine()) result.add(sc.nextLine());
		sc.close();
		return result;
	}
	private static ArrayList<String> read(File sc) throws FileNotFoundException{ return read(new Scanner(sc)); }
}
