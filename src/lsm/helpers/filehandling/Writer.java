package lsm.helpers.filehandling;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Writer {
    private final ImageWriter writer;
    private final ImageOutputStream output;

    Writer(String filename, String filetype) throws IOException {
        this.writer = ImageIO.getImageWritersByFormatName(filetype).next();
        this.output = ImageIO.createImageOutputStream(new File(filename + "." + filetype));
        writer.setOutput(output);
        writer.prepareWriteSequence(null);
    }

    public void add(int[][] image) throws IOException {
        add(Imager.greyScale(image));
    }

    public void add(Color[][] image) throws IOException {
        add(Imager.createImage(image));
    }

    public void add(BufferedImage image) throws IOException {
        writer.writeToSequence(new IIOImage(image, null, null), writer.getDefaultWriteParam());
    }

    public void close() throws IOException {
        output.close();
    }

}
