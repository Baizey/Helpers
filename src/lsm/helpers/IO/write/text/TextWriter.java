package lsm.helpers.IO.write.text;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextWriter {

    public static BufferedWriter getWriter(String filename, boolean overwrite) throws IOException {
        return new BufferedWriter(new FileWriter(filename, !overwrite));
    }
    
}